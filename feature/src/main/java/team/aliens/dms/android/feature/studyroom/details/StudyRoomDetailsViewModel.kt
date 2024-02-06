package team.aliens.dms.android.feature.studyroom.details

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.repository.StudyRoomRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class StudyRoomDetailsViewModel @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : BaseMviViewModel<StudyRoomDetailsUiState, StudyRoomDetailsIntent, StudyRoomDetailsSideEffect>(
    initialState = StudyRoomDetailsUiState.initial(),
) {
    override fun processIntent(intent: StudyRoomDetailsIntent) {
        when (intent) {
            is StudyRoomDetailsIntent.FetchStudyRoomDetails -> {
                this.fetchStudyRoomDetails(
                    studyRoomId = intent.studyRoomId,
                    timeslot = intent.timeslot,
                )
                this.fetchSeatTypes(
                    studyRoomId = intent.studyRoomId,
                )
            }
        }
    }

    private fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studyRoomRepository.fetchStudyRoomDetails(
                studyRoomId = studyRoomId,
                timeslot = timeslot,
            )
        }.onSuccess { fetchedStudyRoomDetails ->
            reduce(
                newState = stateFlow.value.copy(
                    studyRoomDetails = fetchedStudyRoomDetails,
                ),
            )
        }
    }

    private fun fetchSeatTypes(
        studyRoomId: UUID,
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studyRoomRepository.fetchSeatTypes(studyRoomId = studyRoomId)
        }.onSuccess { fetchedSeatTypes ->
            reduce(
                newState = stateFlow.value.copy(
                    seatTypes = fetchedSeatTypes,
                ),
            )
        }
    }
}

internal data class StudyRoomDetailsUiState(
    val studyRoomDetails: StudyRoom.Details?,
    val seatTypes: List<StudyRoom.Seat.Type>?,
) : UiState() {
    companion object {
        fun initial() = StudyRoomDetailsUiState(
            studyRoomDetails = null,
            seatTypes = null,
        )
    }
}

internal sealed class StudyRoomDetailsIntent : Intent() {
    class FetchStudyRoomDetails(
        val studyRoomId: UUID,
        val timeslot: UUID,
    ) : StudyRoomDetailsIntent()
}

internal sealed class StudyRoomDetailsSideEffect : SideEffect()

/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseViewModel2
import team.aliens.dms.android.feature._legacy.util.extractHourFromDate
import team.aliens.dms.android.domain._legacy.exception.RemoteException
import team.aliens.dms.android.domain.model._common.Sex
import team.aliens.dms.android.domain.model.studyroom.ApplySeatInput
import team.aliens.dms.android.domain.model.studyroom.CancelSeatInput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms.android.domain.usecase.studyroom.ApplySeatUseCase
import team.aliens.dms.android.domain.usecase.studyroom.CancelSeatUseCase
import team.aliens.dms.android.domain.usecase.studyroom.FetchSeatTypesUseCase
import team.aliens.dms.android.domain.usecase.studyroom.FetchStudyRoomApplicationTimeUseCase
import team.aliens.dms.android.domain.usecase.studyroom.FetchStudyRoomDetailsUseCase
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.base.UiState
import team.aliens.dms.android.feature._legacy.util.MutableEventFlow
import javax.inject.Inject

@HiltViewModel
class StudyRoomDetailsViewModel @Inject constructor(
    private val fetchStudyRoomDetailUseCase: FetchStudyRoomDetailsUseCase,
    private val applySeatUseCase: ApplySeatUseCase,
    private val fetchApplicationTimeUseCase: FetchStudyRoomApplicationTimeUseCase,
    private val cancelSeatUseCase: CancelSeatUseCase,
    private val fetchSeatTypeUseCase: FetchSeatTypesUseCase,
) : BaseViewModel2<StudyRoomDetailUiState, StudyRoomDetailsViewModel.UiEvent>() {

    sealed class UiEvent : team.aliens.dms.android.feature._legacy.base.UiEvent {

        class ApplySeat(
            val seat: String,
            val timeSlot: UUID,
        ) : UiEvent()

        class CancelApplySeat(
            val seatId: UUID,
            val timeSlot: UUID,
        ) : UiEvent()

        class ChangeSelectedSeat(val seat: String) : UiEvent()
    }

    override val _uiState = MutableStateFlow(StudyRoomDetailUiState())

    private var roomId = _uiState.value.studyRoomId

    private var timeSlot = _uiState.value.timeSlot

    internal fun initStudyRoom(
        roomId: UUID,
        timeSlot: UUID,
    ) {
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
                cancelSeat(
                    seatId = event.seatId,
                    timeSlot = event.timeSlot,
                )
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
                    applySeatInput = ApplySeatInput(
                        seatId = UUID.fromString(seatId),
                        timeSlot = timeSlot,
                    ),
                )
            }.onSuccess {
                fetchStudyRoomDetails(
                    roomId = roomId!!,
                    timeSlot = timeSlot,
                )
            }.onFailure {
                when (it) {
                    is team.aliens.dms.android.domain._legacy.exception.RemoteException.Unauthorized -> emitErrorEvent(
                        application.getString(R.string.NotAvailableSeat),
                    )

                    is team.aliens.dms.android.domain._legacy.exception.RemoteException.Forbidden -> emitErrorEvent(
                        application.getString(R.string.NotStudyRoomApplicateTime),
                    )

                    is team.aliens.dms.android.domain._legacy.exception.RemoteException.Conflict -> emitErrorEvent(
                        application.getString(R.string.SeatAlreadyBeenUsed),
                    )

                    is team.aliens.dms.android.domain._legacy.exception.RemoteException.NotFound -> emitErrorEvent(
                        errorMessage = application.getString(R.string.study_room_seat_not_found),
                    )

                    is KotlinNullPointerException -> { // todo optimize code
                        fetchStudyRoomDetails(
                            roomId = roomId!!,
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

    private fun cancelSeat(
        seatId: UUID,
        timeSlot: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                cancelSeatUseCase(
                    cancelSeatInput = CancelSeatInput(
                        seatId = UUID.fromString(seatId.toString()),
                        timeSlot = timeSlot,
                    ),
                )
            }.onSuccess {
                fetchStudyRoomDetails(
                    roomId = roomId!!,
                    timeSlot = timeSlot,
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
            _uiState.value.currentSeat.emit(UUID.fromString(seat))
        }
    }

    private fun fetchApplyTime() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchApplicationTimeUseCase()
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    startAt = it.startAt.extractHourFromDate(),
                    endAt = it.endAt.extractHourFromDate(),
                )
            }
        }
    }

    private fun fetchStudyRoomDetails(
        roomId: UUID,
        timeSlot: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchStudyRoomDetailUseCase(
                    fetchStudyRoomDetailsInput = FetchStudyRoomDetailsInput(
                        studyRoomId = roomId,
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
                fetchSeatTypeUseCase(
                    fetchSeatTypesInput = FetchSeatTypesInput(
                        studyRoomId = roomId!!,
                    ),
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

data class StudyRoomDetailUiState(
    var studyRoomId: UUID? = null,
    var timeSlot: UUID? = null,
    var currentSeat: MutableEventFlow<UUID> = MutableEventFlow(),
    var startAt: String = "",
    var endAt: String = "",
    var errorMessage: MutableEventFlow<String> = MutableEventFlow(),
    var seatType: FetchSeatTypesOutput = FetchSeatTypesOutput(listOf()),
    var seatBoolean: Boolean = false,
    var studyRoomDetails: FetchStudyRoomDetailsOutput = FetchStudyRoomDetailsOutput(
        floor = 0,
        name = "",
        startTime = "",
        endTime = "",
        totalAvailableSeat = 0,
        inUseHeadcount = 0,
        availableSex = Sex.ALL,
        availableGrade = 0,
        eastDescription = "",
        westDescription = "",
        southDescription = "",
        northDescription = "",
        totalWidthSize = 0,
        totalHeightSize = 0,
        seats = listOf(),
    ),
) : UiState
*/
