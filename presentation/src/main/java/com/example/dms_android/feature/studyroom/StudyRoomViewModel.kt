package com.example.dms_android.feature.studyroom

import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ForbiddenException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.usecase.studyroom.RemoteFetchStudyRoomDetailUseCase
import com.example.domain.usecase.studyroom.RemoteFetchStudyRoomListUseCase
import com.example.domain.usecase.studyroom.RemoteFetchStudyRoomTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyRoomViewModel @Inject constructor(
    private val studyRoomListUseCase: RemoteFetchStudyRoomListUseCase,
    private val studyRoomDetailUseCase: RemoteFetchStudyRoomDetailUseCase,
    private val studyRoomTypeUseCase: RemoteFetchStudyRoomTypeUseCase,
) : BaseViewModel<StudyRoomState, StudyRoomEvent>() {

    private val _studyRoomEffect = MutableEventFlow<Event>()
    val studyRoomEffect = _studyRoomEffect.asEventFlow()

    private val _studyRoomDetailEffect = MutableEventFlow<Event>()
    val studyRoomDetailEffect = _studyRoomDetailEffect.asEventFlow()

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
                setState(
                    state = state.value.copy(
                        roomDetail = it
                    )
                )
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

    fun updateCurrentSeat(
        seatId: String,
    ) {
        setState(
            state = state.value.copy(
                currentSeat = seatId,
            )
        )
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

    //TODO BaseViewModel 에서 처리 필요. StudentRoomEvent
    sealed class Event {
        data class FetchStudyRoomList(val studyRoomListEntity: StudyRoomListEntity) : Event()
        data class FetchStudyDetail(val studyRoomDetailEntity: StudyRoomDetailEntity) : Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object NullPointException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }

    override fun reduceEvent(oldState: StudyRoomState, event: StudyRoomEvent) {
        TODO("Not yet implemented")
    }
}