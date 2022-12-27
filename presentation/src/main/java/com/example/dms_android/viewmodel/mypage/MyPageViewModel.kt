package com.example.dms_android.viewmodel.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.auth.login.SignInState
import com.example.dms_android.feature.mypage.MyPageEvent
import com.example.dms_android.feature.mypage.MyPageState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.dms_android.viewmodel.auth.login.SignInViewModel
import com.example.dms_android.viewmodel.notice.NoticeViewModel
import com.example.domain.entity.mypage.MyPageEntity
import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.ForbiddenException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.exception.UnknownException
import com.example.domain.param.LoginParam
import com.example.domain.usecase.mypage.RemoteMyPageUseCase
import com.example.domain.usecase.mypage.RemotePointUseCase
import com.example.domain.usecase.notice.RemoteNoticeListUseCase
import com.example.local_domain.usecase.mypage.LocalMyPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val remoteMyPageUseCase: RemoteMyPageUseCase,
    val localMyPageUseCase: LocalMyPageUseCase,
    private val remotePointListUseCase: RemotePointUseCase
) : BaseViewModel<MyPageState, MyPageEvent>() {

    override val initialState: MyPageState
        get() = MyPageState.initial()

    private val _myPageViewEffect = MutableEventFlow<Event>()
    var myPageViewEffect = _myPageViewEffect.asEventFlow()

    private val _pointViewEffect = MutableEventFlow<Event>()
    var pointViewEffect = _pointViewEffect.asEventFlow()

    fun fetchMyPage() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMyPageUseCase.execute(Unit)
            }.onSuccess {
                state.value.myPageEntity.gcn = it.gcn
                state.value.myPageEntity.bonusPoint = it.bonusPoint
                state.value.myPageEntity.minusPoint = it.minusPoint
                state.value.myPageEntity.name = it.name
                state.value.myPageEntity.phrase = it.phrase
                state.value.myPageEntity.schoolName = it.schoolName
                state.value.myPageEntity.profileImageUrl = it.profileImageUrl
            }.onFailure {
                when (it) {
                    is NullPointerException -> event(Event.NullPointException)
                    is UnauthorizedException -> event(Event.UnAuthorizedTokenException)
                    is ForbiddenException -> event(Event.CannotConnectException)
                    is TooManyRequestException -> event(Event.TooManyRequestException)
                    is ServerException -> event(Event.InternalServerException)
                    else -> event(Event.UnknownException)
                }
            }
        }
    }

    fun fetchPointList() {
        viewModelScope.launch {
            kotlin.runCatching {
                remotePointListUseCase.execute(state.value.type)
            }.onSuccess {
                event2(Event.FetchPointList(it))
                state.value.totalPoint = it.totalPoint
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
            _myPageViewEffect.emit(event)
            myPageViewEffect = _myPageViewEffect.asEventFlow()
        }
    }

    private fun event2(event: Event) {
        viewModelScope.launch {
            _pointViewEffect.emit(event)
            pointViewEffect = _pointViewEffect.asEventFlow()
        }
    }

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {
        TODO("Not yet implemented")
    }

    sealed class Event {
        data class FetchMyPageValue(val myPageEntity: MyPageEntity) : Event()
        data class FetchPointList(val pointListEntity: PointListEntity): Event()
        object BadRequestException: Event()
        object NullPointException : Event()
        object UnAuthorizedTokenException : Event()
        object CannotConnectException : Event()
        object TooManyRequestException : Event()
        object InternalServerException : Event()
        object UnknownException : Event()
    }
}
