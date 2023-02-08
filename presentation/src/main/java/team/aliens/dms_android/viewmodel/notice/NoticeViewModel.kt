package team.aliens.dms_android.viewmodel.notice

import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.notice.NoticeEvent
import com.example.dms_android.feature.notice.NoticeState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ForbiddenException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.usecase.notice.RemoteNoticeDetailUseCase
import com.example.domain.usecase.notice.RemoteNoticeListUseCase
import com.example.local_domain.usecase.notice.LocalNoticeDetailUseCase
import com.example.local_domain.usecase.notice.LocalNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val remoteNoticeListUseCase: RemoteNoticeListUseCase,
    private val remoteNoticeDetailUseCase: RemoteNoticeDetailUseCase,
    val localNoticeListUseCase: LocalNoticeListUseCase,
    val localNoticeDetailUseCase: LocalNoticeDetailUseCase,
) : BaseViewModel<NoticeState, NoticeEvent>() {

    override val initialState: NoticeState
        get() = NoticeState.initial()

    private val _noticeViewEffect = MutableEventFlow<Event>()
    var noticeViewEffect = _noticeViewEffect.asEventFlow()

    private val _noticeDetailViewEffect = MutableEventFlow<Event>()
    var noticeDetailViewEffect = _noticeDetailViewEffect.asEventFlow()

    fun fetchNoticeList() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteNoticeListUseCase.execute(state.value.type)
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

    fun fetchNoticeDetail(noticeId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteNoticeDetailUseCase.execute(noticeId)
            }.onSuccess {
                event2(Event.FetchNoticeDetail)
                state.value.noticeDetail.title = it.title
                state.value.noticeDetail.content = it.content
                state.value.noticeDetail.createAt = it.createAt
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

    private fun event(event: Event) {
        viewModelScope.launch {
            _noticeViewEffect.emit(event)
            noticeViewEffect = _noticeViewEffect.asEventFlow()
        }
    }

    private fun event2(event: Event) {
        viewModelScope.launch {
            _noticeDetailViewEffect.emit(event)
            noticeDetailViewEffect = _noticeDetailViewEffect.asEventFlow()
        }
    }

    override fun reduceEvent(oldState: NoticeState, event: NoticeEvent) {
        TODO("Not yet implemented")
    }

    sealed class Event {
        data class FetchNoticeList(val noticeListEntity: NoticeListEntity) : Event()
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