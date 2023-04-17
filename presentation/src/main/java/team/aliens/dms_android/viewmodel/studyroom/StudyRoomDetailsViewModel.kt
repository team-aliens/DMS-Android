package team.aliens.dms_android.viewmodel.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.dms_android.util.extractHourFromDate
import team.aliens.domain.exception.ConflictException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.param.StudyRoomDetailParam
import team.aliens.domain.usecase.studyroom.ApplySeatUseCase
import team.aliens.domain.usecase.studyroom.CancelSeatUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchStudyRoomApplicationTimeUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchStudyRoomDetailUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchStudyRoomSeatTypeUseCase
import team.aliens.presentation.R
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StudyRoomDetailsViewModel @Inject constructor(
    private val fetchStudyRoomDetailUseCase: RemoteFetchStudyRoomDetailUseCase,
    private val applySeatUseCase: ApplySeatUseCase,
    private val fetchApplicationTimeUseCase: RemoteFetchStudyRoomApplicationTimeUseCase,
    private val cancelApplySeatUseCase: CancelSeatUseCase,
    private val fetchSeatTypeUseCase: RemoteFetchStudyRoomSeatTypeUseCase,
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
        timeSlot: UUID,
    ) {
        require(roomId.isNotBlank())

        this.roomId = roomId
        this.timeSlot = timeSlot

        fetchStudyRoomDetails(
            roomId = roomId,
            timeSlot = timeSlot,
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
                applySeatUseCase(
                    seatId = UUID.fromString(seatId),
                    timeSlot = timeSlot,
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
                    is NotFoundException -> emitErrorEvent(
                        errorMessage = application.getString(R.string.study_room_seat_not_found),
                    )
                    is KotlinNullPointerException -> { // todo optimize code
                        fetchStudyRoomDetails(
                            roomId = roomId,
                            timeSlot = timeSlot,
                        )
                    }
                    else -> {
                        emitErrorEventFromThrowable(it)
                    }
                }
            }
        }
    }

    private fun cancelSeat() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                cancelApplySeatUseCase(
                    timeSlot = timeSlot!!
                )
            }.onSuccess {
                fetchStudyRoomDetails(
                    roomId = roomId,
                    timeSlot = timeSlot!!,
                )
            }.onFailure {
                it.printStackTrace()
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
                fetchSeatTypeUseCase.execute(
                    studyRoomId = UUID.fromString(roomId),
                )
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
