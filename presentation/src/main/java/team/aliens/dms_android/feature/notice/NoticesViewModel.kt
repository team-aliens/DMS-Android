package team.aliens.dms_android.feature.notice

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.MviViewModel
import team.aliens.dms_android._base.UiEvent
import team.aliens.dms_android._base.UiState
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._exception.RemoteException
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticeDetailsInput
import team.aliens.domain._model.notice.FetchNoticesInput
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain._model.notice.Notice
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ForbiddenException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.notice.FetchNoticeDetailsUseCase
import team.aliens.domain.usecase.notice.FetchNoticesUseCase
import team.aliens.domain.usecase.notice.FetchWhetherNewNoticesExistUseCase
import team.aliens.presentation.R

/*@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
    private val fetchWhetherNewNoticesExistUseCase: FetchWhetherNewNoticesExistUseCase,
) : MviViewModel<NoticeState, NoticeEvent>(NoticeState.initial()) {

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
                        order = uiState.value.type,
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
                onEvent(NoticeEvent.CheckNewNotice(it.newNotices))
            }.onFailure {
                onEvent(NoticeEvent.CheckNewNotice(false))
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
}*/

@HiltViewModel
internal class NoticesViewModel @Inject constructor(
    private val fetchNoticesUseCase: FetchNoticesUseCase,
    private val fetchNoticeDetailsUseCase: FetchNoticeDetailsUseCase,
    private val fetchHasNewNoticesUseCase: FetchWhetherNewNoticesExistUseCase,
    private val application: Application = Application(),
) : MviViewModel<NoticesUiState, NoticesUiEvent>(NoticesUiState.initial()) {

    override fun updateState(event: NoticesUiEvent) {
        when (event) {
            is NoticesUiEvent.FetchNotices -> {
                setState(
                    newState = uiState.value.copy(
                        order = event.order,
                    )
                )
                fetchNotices()
            }

            is NoticesUiEvent.FetchNoticeDetails -> {
                setState(
                    newState = uiState.value.copy(
                        noticeId = event.noticeId,
                    )
                )
                fetchNoticeDetails()
            }

            is NoticesUiEvent.CheckHasNewNotice -> {
                checkHasNewNotice()
            }
        }
    }


    private fun fetchNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticesUseCase(
                    fetchNoticesInput = FetchNoticesInput(
                        order = uiState.value.order,
                    )
                )
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        notices = it.notices,
                    )
                )
            }.onFailure {
                onErrorEvent(
                    throwable = it,
                )
            }
        }
    }

    private fun fetchNoticeDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchNoticeDetailsUseCase(
                    fetchNoticeDetailsInput = FetchNoticeDetailsInput(
                        noticeId = uiState.value.noticeId!!,
                    )
                )
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        notice = Notice(
                            id = it.id,
                            title = it.title,
                            content = it.content,
                            createdAt = it.createdAt,
                        ),
                    )
                )
            }.onFailure {
                onErrorEvent(
                    throwable = it,
                )
            }
        }
    }

    private fun checkHasNewNotice() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchHasNewNoticesUseCase()
            }.onSuccess {
                setState(
                    newState = uiState.value.copy(
                        hasNewNotice = it.newNotices,
                    )
                )
            }.onFailure {
                onErrorEvent(
                    throwable = it,
                )
            }
        }
    }

    private fun onErrorEvent(
        throwable: Throwable,
    ) = with(application.applicationContext) {
        setNoticeErrorMessage(
            message = when (throwable) {
                is RemoteException.BadRequest -> {
                    getString(R.string.error_bad_request)
                }

                is RemoteException.Unauthorized -> {
                    getString(R.string.error_unauthorized)
                }

                is RemoteException.Forbidden -> {
                    getString(R.string.error_forbidden)
                }

                is RemoteException.NotFound -> {
                    getString(R.string.error_not_found)
                }

                is RemoteException.TooManyRequests -> {
                    getString(R.string.error_too_many_request)
                }

                else -> {
                    getString(R.string.error_internal_server)
                }
            }
        )
    }

    private fun setNoticeErrorMessage(
        message: String,
    ) {
        setState(
            newState = uiState.value.copy(
                noticeErrorMessage = message,
            )
        )
    }

}

internal data class NoticesUiState(
    val type: Order,
    val notices: List<FetchNoticesOutput.NoticeInformation>,
    val notice: Notice,
    val hasNewNotice: Boolean = false,
    val order: Order,
    val noticeId: UUID?,
    val noticeErrorMessage: String,
) : UiState {
    companion object {
        fun initial() = NoticesUiState(
            type = Order.NEW,
            notices = emptyList(),
            notice = Notice(
                id = UUID.randomUUID(),
                title = "",
                content = "",
                createdAt = "",
            ),
            hasNewNotice = false,
            order = Order.NEW,
            noticeId = null,
            noticeErrorMessage = "",
        )
    }
}

internal sealed class NoticesUiEvent : UiEvent {
    class FetchNotices(val order: Order) : NoticesUiEvent()
    class FetchNoticeDetails(val noticeId: UUID) : NoticesUiEvent()
    object CheckHasNewNotice : NoticesUiEvent()
}