package team.aliens.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.dms_android.util.extractHourFromDate
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.usecase.studyroom.FetchAvailableStudyRoomTimesUseCase
import team.aliens.domain.usecase.studyroom.FetchStudyRoomApplicationTimeUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchStudyRoomListUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StudyRoomListViewModel @Inject constructor(
    private val studyRoomListUseCase: RemoteFetchStudyRoomListUseCase,
    private val studyRoomApplyTimeUseCase: FetchStudyRoomApplicationTimeUseCase,
    private val studyRoomAvailableTimeListUseCase: FetchAvailableStudyRoomTimesUseCase,
) : BaseViewModel<StudyRoomListUiState, StudyRoomListViewModel.UiEvent>() {

    init {
        fetchApplyTime()
    }

    sealed class UiEvent : BaseEvent {

        data class FetchStudyRooms(
            val timeSlot: UUID,
        ) : UiEvent()

        internal object FetchStudyRoomAvailableTimes : UiEvent()

        internal class FilterStudyRoom(
            studyRoomTime: Any?,
        ) : UiEvent() // todo implement
    }

    override val _uiState = MutableStateFlow(StudyRoomListUiState())

    override fun onEvent(
        event: UiEvent,
    ) {
        when (event) {
            is UiEvent.FetchStudyRooms -> {
                fetchStudyRoomList(
                    timeSlot = event.timeSlot,
                )
            }
            is UiEvent.FetchStudyRoomAvailableTimes -> {
                fetchStudyRoomAvailableTimeList()
            }
            is UiEvent.FilterStudyRoom -> {
                // todo
            }
        }
    }

    private fun fetchApplyTime() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomApplyTimeUseCase()
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    startAt = resultEntity.startAt.extractHourFromDate(),
                    endAt = resultEntity.endAt.extractHourFromDate(),
                    hasApplyTime = true,
                )
            } else {
                val exception = result.exceptionOrNull()

                if (exception !is NotFoundException) {

                    emitErrorEventFromThrowable(exception)
                }
            }
        }
    }

    private fun fetchStudyRoomList(
        timeSlot: UUID,
    ) {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomListUseCase.execute(
                    data = timeSlot,
                )
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    studyRooms = resultEntity.studyRooms.toInformation(),
                )
            } else {
                if (result.exceptionOrNull() !is NullPointerException) {
                    emitErrorEventFromThrowable(result.exceptionOrNull())
                }
            }
        }
    }

    private fun fetchStudyRoomAvailableTimeList() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyRoomAvailableTimeListUseCase()
            }.onSuccess { resultEntity ->
                _uiState.value = _uiState.value.copy(
                    studyRoomAvailableTime = resultEntity.timeSlots,
                )
            }.onFailure {
                emitErrorEventFromThrowable(
                    throwable = it
                )
            }
        }
    }
}
