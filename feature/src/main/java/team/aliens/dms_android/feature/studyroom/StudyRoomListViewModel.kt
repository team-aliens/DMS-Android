package team.aliens.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature._legacy.base.BaseViewModel2
import team.aliens.dms_android.feature._legacy.util.extractHourFromDate
import team.aliens.dms_android.domain.exception.RemoteException
import team.aliens.dms_android.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.dms_android.domain.usecase.studyroom.FetchAvailableStudyRoomTimesUseCase
import team.aliens.dms_android.domain.usecase.studyroom.FetchStudyRoomApplicationTimeUseCase
import team.aliens.dms_android.domain.usecase.studyroom.FetchStudyRoomsUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StudyRoomListViewModel @Inject constructor(
    private val fetchStudyRoomsUseCase: FetchStudyRoomsUseCase,
    private val studyRoomApplyTimeUseCase: FetchStudyRoomApplicationTimeUseCase,
    private val studyRoomAvailableTimeListUseCase: FetchAvailableStudyRoomTimesUseCase,
) : BaseViewModel2<StudyRoomListUiState, StudyRoomListViewModel.UiEvent>() {

    init {
        fetchApplyTime()
    }

    sealed class UiEvent : team.aliens.dms_android.feature._legacy.base.UiEvent {

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

                if (exception !is RemoteException.NotFound) {

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
                fetchStudyRoomsUseCase(
                    fetchStudyRoomsInput = FetchStudyRoomsInput(
                        timeSlot = timeSlot,
                    ),
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
