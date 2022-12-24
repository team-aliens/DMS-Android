package com.example.dms_android.viewmodel.notice

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.MainActivity
import com.example.dms_android.feature.mypage.MyPageEvent
import com.example.dms_android.feature.mypage.MyPageState
import com.example.dms_android.feature.notice.NoticeEvent
import com.example.dms_android.feature.notice.NoticeState
import com.example.dms_android.util.ConnectivityObserver
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.NetworkConnectivityObserver
import com.example.dms_android.util.asEventFlow
import com.example.domain.entity.notice.NoticeDetailEntity
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val remoteNoticeListUseCase: RemoteNoticeListUseCase,
    val remoteNoticeDetailUseCase: RemoteNoticeDetailUseCase,
    val localNoticeListUseCase: LocalNoticeListUseCase,
    val localNoticeDetailUseCase: LocalNoticeDetailUseCase,
): BaseViewModel<NoticeState, NoticeEvent>() {

    override val initialState: NoticeState
        get() = NoticeState.initial()


    private val _noticeViewEffect = MutableEventFlow<Event>()
    var noticeViewEffect = _noticeViewEffect.asEventFlow()

    fun fetchNoticeList() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteNoticeListUseCase.execute(state.value.type)
                    .collect {
                        event(Event.FetchNoticeList(it))
                    }
            }.onFailure {
                when(it) {
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

    fun fetchNoticeDetail(noticeId: UUID) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteNoticeDetailUseCase.execute(noticeId)
                    .collect {
                        event(Event.FetchNoticeDetail(it))
                    }
            }.onFailure {
                when(it) {
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

    private fun event(event: Event) {
        viewModelScope.launch {
            _noticeViewEffect.emit(event)
            noticeViewEffect = _noticeViewEffect.asEventFlow()
        }
    }

    override fun reduceEvent(oldState: NoticeState, event: NoticeEvent) {
        TODO("Not yet implemented")
    }

    sealed class Event {
        data class FetchNoticeList(val noticeListEntity: NoticeListEntity): Event()
        data class FetchNoticeDetail(val noticeDetailEntity: NoticeDetailEntity): Event()
        object NullPointException: Event()
        object BadRequestException: Event()
        object UnAuthorizedTokenException: Event()
        object CannotConnectException: Event()
        object TooManyRequestException: Event()
        object InternalServerException: Event()
        object UnknownException: Event()
    }
}