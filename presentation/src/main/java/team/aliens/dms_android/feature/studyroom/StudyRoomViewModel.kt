package team.aliens.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.domain.exception.*
import team.aliens.domain.usecase.studyroom.*
import javax.inject.Inject

@HiltViewModel
class StudyRoomViewModel @Inject constructor(
    private val studyRoomListUseCase: RemoteFetchStudyRoomListUseCase,
    private val studyRoomDetailUseCase: RemoteFetchStudyRoomDetailUseCase,
    private val studyRoomTypeUseCase: RemoteFetchStudyRoomTypeUseCase,
    private val studyApplySeatUseCase: RemoteApplySeatUseCase,
    private val studyCancelApplySeat: RemoteCancelApplySeat,
    private val studyRoomApplyTimeUseCase: RemoteFetchApplySeatUseCase,
) : BaseViewModel<StudyRoomState, StudyRoomEvent>() {

    private val _studyRoomEffect = MutableEventFlow<Event>()
    val studyRoomEffect = _studyRoomEffect.asEventFlow()

    private val _studyRoomDetailEffect = MutableEventFlow<Event>()
    val studyRoomDetailEffect = _studyRoomDetailEffect.asEventFlow()

    private val _studyRoomApplyEffect = MutableEventFlow<Event>()
    val studyRoomApplyEffect = _studyRoomApplyEffect.asEventFlow()

    private val _studyRoomTimeEffect = MutableEventFlow<Event>()
    val studyRoomTimeEffect = _studyRoomTimeEffect.asEventFlow()

    override val initialState: StudyRoomState
        get() = StudyRoomState.initial()

    fun fetchStudyRoomList() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyRoomListUseCase.execute(Unit)
            }.onSuccess {
                event(Event.FetchStudyRoomList(it))
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(Event.BadRequestException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is NullPointerException -> event(Event.NullPointException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun fetchStudyRoomDetail(roomId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                studyRoomDetailUseCase.execute(roomId)
            }.onSuccess {
                setState(state = state.value.copy(roomDetail = it))
            }.onFailure {
                when (it) {
                    is BadRequestException -> event2(Event.BadRequestException)
                    is UnauthorizedException -> event2(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event2(Event.CannotConnectException)
                    is TooManyRequestException -> event2(Event.TooManyRequestException)
                    is NullPointerException -> event2(Event.NullPointException)
                    is ServerException -> event2(Event.InternalServerException)
                    else -> event2(Event.UnknownException)
                }
            }
        }
    }

    fun applyStudyRoomSeat(
        currentSeat: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                studyApplySeatUseCase.execute(currentSeat)
            }.onSuccess {
                event3(Event.ApplyStudyRoom)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event3(Event.BadRequestException)
                    is UnauthorizedException -> event3(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event3(Event.CannotConnectException)
                    is TooManyRequestException -> event3(Event.TooManyRequestException)
                    is ConflictException -> event3(Event.ConflictException)
                    is NullPointerException -> event3(Event.NullPointException)
                    is ServerException -> event3(Event.InternalServerException)
                    else -> event3(Event.UnknownException)
                }
            }
        }
    }

    fun cancelStudyRoomSeat() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyCancelApplySeat.execute(Unit)
            }.onSuccess {
                event3(Event.CancelStudyRoom)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event3(Event.BadRequestException)
                    is UnauthorizedException -> event3(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event3(Event.CannotConnectException)
                    is TooManyRequestException -> event3(Event.TooManyRequestException)
                    is NullPointerException -> event3(Event.NullPointException)
                    is ServerException -> event3(Event.InternalServerException)
                    else -> event3(Event.UnknownException)
                }
            }
        }
    }

    fun fetchApplyTime() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyRoomApplyTimeUseCase.execute(Unit)
            }.onSuccess {
                event4(Event.RoomApplyTime)
                setState(state = state.value.copy(
                    startAt = it.startAt.toHour(),
                    endAt = it.endAt.toHour(),
                ))
            }.onFailure {
                when (it) {
                    is BadRequestException -> event4(Event.BadRequestException)
                    is UnauthorizedException -> event4(Event.UnAuthorizedTokenException)
                    is TooManyRequestException -> event4(Event.TooManyRequestException)
                    is NullPointerException -> event4(Event.NullPointException)
                    is TooManyRequestException -> event4(Event.TooManyRequestException)
                    is ServerException -> event4(Event.InternalServerException)
                    else -> event4(Event.UnknownException)
                }
            }
        }
    }

    private fun String.toHour(): String {
        return this.substring(0, 5)
    }

    fun updateCurrentSeat(
        seatId: String,
    ) {
        setState(state = state.value.copy(
            currentSeat = seatId,
        ))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _studyRoomEffect.emit(event)
        }
    }

    private fun event2(event: Event) {
        viewModelScope.launch {
            _studyRoomDetailEffect.emit(event)
        }
    }

    private fun event3(event: Event) {
        viewModelScope.launch {
            _studyRoomApplyEffect.emit(event)
        }
    }

    private fun event4(event: Event) {
        viewModelScope.launch {
            _studyRoomTimeEffect.emit(event)
        }
    }

    //TODO BaseViewModel 에서 처리 필요. StudentRoomEvent
    sealed class Event {
        data class FetchStudyRoomList(val studyRoomListEntity: StudyRoomListEntity) : Event()
        data class FetchStudyDetail(val studyRoomDetailEntity: StudyRoomDetailEntity) : Event()
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
    }

    override fun reduceEvent(oldState: StudyRoomState, event: StudyRoomEvent) {
        TODO("Not yet implemented")
    }
}