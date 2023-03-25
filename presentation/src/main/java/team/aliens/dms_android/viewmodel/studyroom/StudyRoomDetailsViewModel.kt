package team.aliens.dms_android.viewmodel.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.dms_android.util.extractHourFromDate
import team.aliens.domain.exception.ConflictException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.param.ApplyStudyRoomParam
import team.aliens.domain.param.StudyRoomDetailParam
import team.aliens.domain.usecase.studyroom.*
import team.aliens.presentation.R

@HiltViewModel
class StudyRoomDetailsViewModel @Inject constructor(
    private val fetchStudyRoomDetailUseCase: RemoteFetchStudyRoomDetailUseCase,
    private val applySeatUseCase: RemoteApplySeatUseCase,
    private val fetchApplicationTimeUseCase: RemoteFetchStudyRoomApplicationTimeUseCase,
    private val cancelApplySeatUseCase: RemoteCancelApplySeatUseCase,
    private val fetchSeatTypeUseCase: RemoteFetchStudyRoomSeatTypeUseCase
) : BaseViewModel<StudyRoomDetailUiState, StudyRoomDetailsViewModel.UiEvent>() {

    sealed class UiEvent : BaseEvent {

        class ApplySeat(
            val seat: String,
            val timeSlot: UUID,
        ) : UiEvent()

        object CancelApplySeat : UiEvent()

        class ChangeSelectedSeat(val seat: String) : UiEvent()
    }

    override val _uiState = MutableStateFlow(StudyRoomDetailUiState())

    private var roomId = _uiState.value.studyRoomId
    private var timeSlot = _uiState.value.timeSlot

    internal fun initStudyRoom(
        roomId: String,
        timeSlot: UUID?,
    ) {
        require(roomId.isNotBlank())

        this.roomId = roomId
        this.timeSlot = timeSlot

        fetchStudyRoomDetails(
            roomId = roomId,
            timeSlot = timeSlot!!,
        )

        fetchApplyTime()
        fetchRoomSeatType()
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ApplySeat -> {
                applySeat(
                    event.seat,
                    event.timeSlot,
                )
            }
            is UiEvent.CancelApplySeat -> {
                cancelSeat()
            }
            is UiEvent.ChangeSelectedSeat -> {
                changeSelectedSeat(
                    seat = event.seat,
                )
            }
        }
    }

    private fun applySeat(
        seatId: String,
        timeSlot: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                applySeatUseCase.execute(
                    ApplyStudyRoomParam(
                        seatId = seatId,
                        timeSlot = timeSlot,
                    )
                )
            }.onSuccess {
                fetchStudyRoomDetails(
                    roomId = roomId,
                    timeSlot = timeSlot,
                )
            }.onFailure {
                when (it) {
                    is UnauthorizedException -> emitErrorEvent(
                        application.getString(R.string.NotAvailableSeat),
                    )
                    is ForbiddenException -> emitErrorEvent(
                        application.getString(R.string.NotStudyRoomApplicateTime),
                    )
                    is ConflictException -> emitErrorEvent(
                        application.getString(R.string.SeatAlreadyBeenUsed),
                    )
                    is KotlinNullPointerException -> { // todo optimize code
                        fetchStudyRoomDetails(
                            roomId = roomId,
                            timeSlot = timeSlot,
                        )
                    }
                }
            }.onFailure {
                emitErrorEventFromThrowable(it)
            }
        }
    }

    private fun cancelSeat() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                cancelApplySeatUseCase.execute(
                    data = timeSlot!!
                )
            }.onSuccess {
                fetchStudyRoomDetails(
                    roomId = roomId,
                    timeSlot = timeSlot!!,
                )
            }.onFailure {
                emitErrorEventFromThrowable(it)
            }
        }
    }

    private fun changeSelectedSeat(
        seat: String,
    ) {
        viewModelScope.launch {
            _uiState.value.currentSeat.emit(seat)
        }
    }

    private fun fetchApplyTime() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchApplicationTimeUseCase.execute(Unit)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    startAt = it.startAt.extractHourFromDate(),
                    endAt = it.endAt.extractHourFromDate(),
                )
            }.onFailure {
                emitErrorEventFromThrowable(it)
            }
        }
    }

    private fun fetchStudyRoomDetails(
        roomId: String,
        timeSlot: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchStudyRoomDetailUseCase.execute(
                    studyRoomDetailParam = StudyRoomDetailParam(
                        roomId = roomId,
                        timeSlot = timeSlot,
                    ),
                )
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    studyRoomDetails = it,
                )
            }.onFailure {
                emitErrorEventFromThrowable(it)
            }
        }
    }

    private fun fetchRoomSeatType() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchSeatTypeUseCase.execute(Unit)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    seatType = it
                )
                _uiState.value.seatBoolean = true
            }
        }
    }

    private fun emitErrorEvent(
        errorMessage: String,
    ) {
        viewModelScope.launch {
            _uiState.value.errorMessage.emit(errorMessage)
        }
    }
}
