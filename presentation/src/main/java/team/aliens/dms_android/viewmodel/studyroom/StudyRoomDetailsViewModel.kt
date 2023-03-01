package team.aliens.dms_android.viewmodel.studyroom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.domain.exception.ConflictException
import team.aliens.domain.usecase.studyroom.RemoteApplySeatUseCase
import team.aliens.domain.usecase.studyroom.RemoteCancelApplySeatUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchStudyRoomApplicationTimeUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchStudyRoomDetailUseCase
import team.aliens.presentation.R
import javax.inject.Inject

@HiltViewModel
class StudyRoomDetailsViewModel @Inject constructor(
    private val fetchStudyRoomDetailUseCase: RemoteFetchStudyRoomDetailUseCase,
    private val applySeatUseCase: RemoteApplySeatUseCase,
    private val fetchApplicationTimeUseCase: RemoteFetchStudyRoomApplicationTimeUseCase,
    private val cancelApplySeatUseCase: RemoteCancelApplySeatUseCase,
) : BaseViewModel<StudyRoomDetailUiState, StudyRoomDetailsViewModel.UiEvent>() {

    sealed class UiEvent : BaseEvent {

        class ApplySeat(val seat: String) : UiEvent()

        object CancelApplySeat : UiEvent()

        class ChangeSelectedSeat(val seat: String) : UiEvent()
    }

    override val _uiState = mutableStateOf(StudyRoomDetailUiState())

    internal fun initStudyRoom(
        studyRoomId: String,
    ) {
        require(studyRoomId.isNotBlank())

        _uiState.value.studyRoomId = studyRoomId

        fetchStudyRoomDetails(studyRoomId)

        fetchApplyTime()
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ApplySeat -> {
                applySeat(event.seat)
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
        seat: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                applySeatUseCase.execute(seat)
            }.onSuccess {
                fetchStudyRoomDetails(
                    studyRoomId = _uiState.value.studyRoomId,
                )
            }.onFailure {
                when (it) {
                    is ConflictException -> emitErrorEvent(
                        application.getString(R.string.SeatAlreadyBeenUsed),
                    )
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
                cancelApplySeatUseCase.execute(Unit)
            }.onSuccess {
                fetchStudyRoomDetails(
                    studyRoomId = _uiState.value.studyRoomId,
                )
            }.onFailure {
                emitErrorEventFromThrowable(it)
            }
        }
    }

    private fun changeSelectedSeat(
        seat: String,
    ) {
        _uiState.value = _uiState.value.copy(
            currentSeat = seat,
        )
    }

    private fun fetchApplyTime() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchApplicationTimeUseCase.execute(Unit)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    startAt = it.startAt,
                    endAt = it.endAt,
                )
            }.onFailure {
                emitErrorEventFromThrowable(it)
            }
        }
    }

    private fun fetchStudyRoomDetails(
        studyRoomId: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchStudyRoomDetailUseCase.execute(studyRoomId)
            }.onSuccess {
                _uiState.value.studyRoomDetails = it
            }.onFailure {
                emitErrorEventFromThrowable(it)
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
