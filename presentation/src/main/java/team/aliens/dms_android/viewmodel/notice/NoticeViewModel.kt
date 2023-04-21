package team.aliens.dms_android.viewmodel.notice

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.BaseViewModel
import team.aliens.dms_android.feature.notice.NoticeDetail
import team.aliens.dms_android.feature.notice.NoticeEvent
import team.aliens.dms_android.feature.notice.NoticeState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
    private val fetchWhetherNewNoticesExistUseCase: FetchWhetherNewNoticesExistUseCase,
) : BaseViewModel<NoticeState, NoticeEvent>() {

    override val initialState: NoticeState
        get() = NoticeState.initial()

    private val _noticeViewEffect = MutableEventFlow<Event>()
    var noticeViewEffect = _noticeViewEffect.asEventFlow()

    private val _noticeDetailViewEvent = MutableEventFlow<Event>()
    var noticeDetailViewEvent = _noticeDetailViewEvent.asEventFlow()

    private val _noticeDetailViewEffect = MutableStateFlow(NoticeDetail())
    var noticeDetailViewEffect = _noticeDetailViewEffect.asStateFlow()

    fun fetchNoticeList() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticesUseCase(
                    fetchNoticesInput = FetchNoticesInput(
                        order = state.value.type,
                    ),
                )
            }.onSuccess {
                event(Event.FetchNoticeList(it))
            }.onFailure {
                when (it) {
                    is NullPointerException -> event(Event.NullPointException)
                    is BadRequestException -> event(Event.BadRequestException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun fetchNoticeDetail(
        noticeId: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticeDetailsUseCase(
                    fetchNoticeDetailsInput = FetchNoticeDetailsInput(
                        noticeId = noticeId,
                    ),
                )
            }.onSuccess {
                emitNoticeDetailState(
                    NoticeDetail(
                        title = it.title,
                        content = it.content,
                        createAt = "${it.createdAt.split('.')[0].split('T')[0]} " + it.createdAt.split(
                            '.'
                        )[0].split('T')[1].split(':')[0] + ":" + it.createdAt.split('.')[0].split(
                            'T'
                        )[1].split(':')[1]
                    )
                )
            }.onFailure {
                when (it) {
                    is NullPointerException -> event2(Event.NullPointException)
                    is BadRequestException -> event2(Event.BadRequestException)
                    is UnauthorizedException -> event2(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event2(Event.CannotConnectException)
                    is TooManyRequestException -> event2(Event.TooManyRequestException)
                    is ServerException -> event2(Event.InternalServerException)
                    else -> event2(Event.UnknownException)
                }
            }
        }
    }

    internal fun checkNewNotice() {
        viewModelScope.launch {
            kotlin.runCatching {
                fetchWhetherNewNoticesExistUseCase()
            }.onSuccess {
                sendEvent(NoticeEvent.CheckNewNotice(it.newNotices))
            }.onFailure {
                sendEvent(NoticeEvent.CheckNewNotice(false))
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _noticeViewEffect.emit(event)
            noticeViewEffect = _noticeViewEffect.asEventFlow()
        }
    }

    private fun event2(event: Event) {
        viewModelScope.launch {
            _noticeDetailViewEvent.emit(event)
        }
    }

    private fun emitNoticeDetailState(
        noticeDetail: NoticeDetail,
    ) {
        viewModelScope.launch {
            _noticeDetailViewEffect.emit(
                noticeDetail,
            )
        }
    }

    override fun reduceEvent(oldState: NoticeState, event: NoticeEvent) {
        when (event) {
            is NoticeEvent.CheckNewNotice -> {
                setState(oldState.copy(hasNewNotice = event.hasNewNotice))
            }
        }
    }

    sealed class Event {
        data class FetchNoticeList(val fetchNoticesOutput: FetchNoticesOutput) : Event()
        object FetchNoticeDetail : Event()
        object NullPointException : Event()
        object BadRequestException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}