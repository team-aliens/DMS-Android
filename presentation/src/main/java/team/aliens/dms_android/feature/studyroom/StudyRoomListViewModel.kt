package team.aliens.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.dms_android.util.extractHourFromDate
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.usecase.studyroom.*

@HiltViewModel
class StudyRoomListViewModel @Inject constructor(
    private val studyRoomListUseCase: RemoteFetchStudyRoomListUseCase,
    private val studyRoomTypeUseCase: RemoteFetchStudyRoomSeatTypeUseCase,
    private val studyApplySeatUseCase: RemoteApplySeatUseCase,
    private val studyCancelApplySeat: RemoteCancelApplySeatUseCase,
    private val studyRoomApplyTimeUseCase: RemoteFetchStudyRoomApplicationTimeUseCase,
    private val currentStudyRoomOptionUseCase: RemoteFetchCurrentStudyRoomOptionUseCase,
    private val studyRoomAvailableTimeListUseCase: RemoteFetchStudyRoomAvailableTimeListUseCase,
) : BaseViewModel<StudyRoomListUiState, StudyRoomListViewModel.UiEvent>() {

    init {
        fetchApplyTime()
    }

    sealed class UiEvent : BaseEvent {

        internal object FetchStudyRooms : UiEvent()

        internal object FetchStudyRoomAvailableTimes : UiEvent()

        internal data class SetStudyRoomAvailableTime(
            val timeSlot: UUID,
        ) : UiEvent()

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
                fetchStudyRoomList()
            }
            is UiEvent.FetchStudyRoomAvailableTimes -> {
                fetchStudyRoomAvailableTimeList()
            }
            is UiEvent.SetStudyRoomAvailableTime -> {
                setStudyRoomAvailableTime(
                    timeSlot = event.timeSlot,
                )
            }
            is UiEvent.FilterStudyRoom -> {
                // todo
            }
        }
    }

    private fun fetchApplyTime() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomApplyTimeUseCase.execute(Unit)
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

                if (exception != NotFoundException()) {
                    emitErrorEventFromThrowable(exception)
                }
            }
        }
    }

    private fun fetchStudyRoomList() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomListUseCase.execute(_uiState.value.timeSlot!!)
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    studyRooms = resultEntity.studyRooms.toInformation(),
                )
            } else {
                emitErrorEventFromThrowable(result.exceptionOrNull())
            }
        }
    }

    private fun fetchStudyRoomAvailableTimeList() {
        viewModelScope.launch {
            studyRoomAvailableTimeListUseCase()
                .onSuccess { resultEntity ->
                    _uiState.value = _uiState.value.copy(
                        studyRoomAvailableTime = resultEntity.timeSlots,
                    )
                }
                .onFailure {
                    emitErrorEventFromThrowable(
                        throwable = it
                    )
                }
        }
    }

    private fun setStudyRoomAvailableTime(
        timeSlot: UUID,
    ) {
        _uiState.value = _uiState.value.copy(
            timeSlot = timeSlot,
        )
    }
}

