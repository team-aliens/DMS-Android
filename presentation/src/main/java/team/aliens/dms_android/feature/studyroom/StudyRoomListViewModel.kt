package team.aliens.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.dms_android.util.extractHourFromDate
import team.aliens.domain.usecase.studyroom.*
import javax.inject.Inject

@HiltViewModel
class StudyRoomListViewModel @Inject constructor(
    private val studyRoomListUseCase: RemoteFetchStudyRoomListUseCase,
    private val studyRoomTypeUseCase: RemoteFetchStudyRoomSeatTypeUseCase,
    private val studyApplySeatUseCase: RemoteApplySeatUseCase,
    private val studyCancelApplySeat: RemoteCancelApplySeatUseCase,
    private val studyRoomApplyTimeUseCase: RemoteFetchStudyRoomApplicationTimeUseCase,
    private val currentStudyRoomOptionUseCase: RemoteFetchCurrentStudyRoomOptionUseCase,
) : BaseViewModel<StudyRoomListUiState, StudyRoomListViewModel.UiEvent>() {

    init {
        fetchStudyRoomList()
        fetchApplyTime()
    }

    sealed class UiEvent : BaseEvent {

        internal object FetchStudyRooms : UiEvent()

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

                //emitStudyRoomTimeEvent(Event.RoomApplyTime)

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    startAt = resultEntity.startAt.extractHourFromDate(),
                    endAt = resultEntity.endAt.extractHourFromDate(),
                )
            } else {
                emitErrorEventFromThrowable(
                    result.exceptionOrNull(),
                )
            }
        }
    }

    internal fun fetchStudyRoomList() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomListUseCase.execute(Unit)
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    studyRooms = resultEntity.studyRooms.toInformation(),
                )
            } else {
                emitErrorEventFromThrowable(
                    result.exceptionOrNull(),
                )
            }
        }
    }

    //    private val _studyRoomEffect = MutableEventFlow<Event>()
//    val studyRoomEffect = _studyRoomEffect.asEventFlow()
//
//    private val _studyRoomDetailEffect = MutableEventFlow<Event>()
//    val studyRoomDetailEffect = _studyRoomDetailEffect.asEventFlow()
//
//    private val _studyRoomApplyEffect = MutableEventFlow<Event>()
//    val studyRoomApplyEffect = _studyRoomApplyEffect.asEventFlow()
//
//    private val _studyRoomTimeEffect = MutableEventFlow<Event>()
//    val studyRoomTimeEffect = _studyRoomTimeEffect.asEventFlow()
//
//    private val _currentStudyRoomOptionEffect = MutableEventFlow<Event>()
//    val currentStudyRoomOptionEffect = _currentStudyRoomOptionEffect.asEventFlow()

/*    private fun emitStudyRoomEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomEffect.emit(event)
        }
    }

    private fun emitStudyRoomDetailEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomDetailEffect.emit(event)
        }
    }*/


    /*fun applyStudyRoomSeat(
        currentSeat: String,
    ) {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyApplySeatUseCase.execute(currentSeat)
            }

            emitStudyRoomApplyEvent(
                if (result.isSuccess) {
                    Event.ApplyStudyRoom
                } else {
                    getEventFromThrowable(
                        result.exceptionOrNull(),
                    )
                },
            )
        }
    }

    fun cancelStudyRoomSeat() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyCancelApplySeat.execute(Unit)
            }

            emitStudyRoomApplyEvent(
                if (result.isSuccess) {
                    Event.CancelStudyRoom
                } else {
                    getEventFromThrowable(
                        result.exceptionOrNull(),
                    )
                },
            )
        }
    }

    private fun emitStudyRoomApplyEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomApplyEffect.emit(event)
        }
    }*/


/*    private fun emitStudyRoomTimeEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomTimeEffect.emit(event)
        }
    }*/


/*    fun updateCurrentSeat(
        seatId: String,
    ) {
        setState(
            state = state.value.copy(
                currentSeat = seatId,
            ),
        )
    }*/

    /*internal fun fetchCurrentStudyRoomOption() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                currentStudyRoomOptionUseCase.execute(Unit)
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value.current

                _currentStudyRoomOptionEffect.emit(
                    Event.FetchCurrentStudyRoomOption(
                        floor = resultEntity.floor,
                        name = resultEntity.name,
                    )
                )
            } else {
                emitCurrentStudyRoomOptionEvent(
                    event = getEventFromThrowable(
                        throwable = result.exceptionOrNull(),
                    )
                )
            }

        }
    }

    private fun emitCurrentStudyRoomOptionEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _currentStudyRoomOptionEffect.emit(event)
        }
    }


    //TODO BaseViewModel 에서 처리 필요. StudentRoomEvent
    sealed class Event {
        data class FetchStudyRoomList(val studyRoomListEntity: StudyRoomListEntity) : Event()
        data class FetchStudyDetail(val studyRoomDetailEntity: StudyRoomDetailEntity) : Event()
        data class FetchCurrentStudyRoomOption(
            val floor: Long,
            val name: String,
        ) : Event()

        object RoomApplyTime : Event()
        object ApplyStudyRoom : Event()
        object CancelStudyRoom : Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object NotFoundException : Event()
        object TooManyRequestException : Event()
        object NullPointException : Event()
        object ConflictException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }*/
}


// todo remove ---------

const val firstPart = "22:00 ~ 22:50"
const val secondPart = "23:00 ~ 23:50"

// todo ----------------

