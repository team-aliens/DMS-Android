package team.aliens.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.studyroom.StudyRoomViewModel.Event
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
    private val currentStudyRoomOptionUseCase: RemoteFetchCurrentStudyRoomOptionUseCase,
) : BaseViewModel<StudyRoomState, StudyRoomEvent>() {

    init {
        fetchApplyTime()
    }

    private val _studyRoomEffect = MutableEventFlow<Event>()
    val studyRoomEffect = _studyRoomEffect.asEventFlow()

    private val _studyRoomDetailEffect = MutableEventFlow<Event>()
    val studyRoomDetailEffect = _studyRoomDetailEffect.asEventFlow()

    private val _studyRoomApplyEffect = MutableEventFlow<Event>()
    val studyRoomApplyEffect = _studyRoomApplyEffect.asEventFlow()

    private val _studyRoomTimeEffect = MutableEventFlow<Event>()
    val studyRoomTimeEffect = _studyRoomTimeEffect.asEventFlow()

    private val _currentStudyRoomOptionEffect = MutableEventFlow<Event>()
    val currentStudyRoomOptionEffect = _currentStudyRoomOptionEffect.asEventFlow()

    override val initialState: StudyRoomState
        get() = StudyRoomState.getDefaultInstance()


    internal fun fetchStudyRoomList() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomListUseCase.execute(Unit)
            }

            emitStudyRoomEvent(
                if (result.isSuccess) {

                    val resultEntity = result.getOrThrow()

                    Event.FetchStudyRoomList(resultEntity)
                } else {
                    getEventFromThrowable(
                        result.exceptionOrNull(),
                    )
                },
            )
        }
    }

    private fun emitStudyRoomEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomEffect.emit(event)
        }
    }


    fun fetchStudyRoomDetail(
        studyRoomId: String,
    ) {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomDetailUseCase.execute(studyRoomId)
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                setState(
                    state.value.copy(
                        roomDetail = resultEntity,
                    ),
                )
            } else {
                emitStudyRoomDetailEvent(
                    getEventFromThrowable(
                        result.exceptionOrNull(),
                    )
                )
            }
        }
    }

    private fun emitStudyRoomDetailEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomDetailEffect.emit(event)
        }
    }


    fun applyStudyRoomSeat(
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
    }


    fun fetchApplyTime() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomApplyTimeUseCase.execute(Unit)
            }

            if (result.isSuccess) {

                emitStudyRoomTimeEvent(Event.RoomApplyTime)

                val resultEntity = result.getOrThrow()

                setState(
                    state = state.value.copy(
                        startAt = resultEntity.startAt.extractHourFromDate(),
                        endAt = resultEntity.endAt.extractHourFromDate(),
                    ),
                )
            } else {
                emitStudyRoomApplyEvent(
                    getEventFromThrowable(
                        result.exceptionOrNull(),
                    ),
                )
            }
        }
    }

    private fun emitStudyRoomTimeEvent(
        event: Event,
    ) {
        viewModelScope.launch {
            _studyRoomTimeEffect.emit(event)
        }
    }


    fun updateCurrentSeat(
        seatId: String,
    ) {
        setState(
            state = state.value.copy(
                currentSeat = seatId,
            ),
        )
    }

    internal fun fetchCurrentStudyRoomOption() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                currentStudyRoomOptionUseCase.execute(Unit)
            }

            result.getOrNull()?.run {
                if (result.isSuccess) {
                    _currentStudyRoomOptionEffect.emit(
                        Event.FetchCurrentStudyRoomOption(
                            floor = floor,
                            name = name,
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
    }

    override fun reduceEvent(oldState: StudyRoomState, event: StudyRoomEvent) {}
}

private fun String.extractHourFromDate(): String {
    return this.substring(0, 5)
}

// 추후 BaseViewModel로 Event 위임을 위해 클래스의 외부 함수로 구현
private fun getEventFromThrowable(
    throwable: Throwable?,
): Event {
    return when (throwable) {
        is BadRequestException -> Event.BadRequestException
        is NotFoundException -> Event.NotFoundException
        is UnauthorizedException -> Event.UnAuthorizedTokenException
        is ForbiddenException -> Event.CannotConnectException
        is TooManyRequestException -> Event.TooManyRequestException
        is NullPointerException -> Event.NullPointException
        is ServerException -> Event.InternalServerException
        else -> Event.UnknownException
    }
}
