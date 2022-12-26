package com.example.dms_android.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.auth.login.SignInState
import com.example.dms_android.feature.mypage.MyPageEvent
import com.example.dms_android.feature.mypage.MyPageState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.dms_android.viewmodel.auth.login.SignInViewModel
import com.example.domain.entity.mypage.MyPageEntity
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
import com.example.local_domain.usecase.mypage.LocalMyPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val remoteMyPageUseCase: RemoteMyPageUseCase,
    val localMyPageUseCase: LocalMyPageUseCase,
): BaseViewModel<MyPageState, MyPageEvent>() {

    override val initialState: MyPageState
        get() = MyPageState.initial()

    private val _myPageViewEffect = MutableEventFlow<Event>()
    var myPageViewEffect = _myPageViewEffect.asEventFlow()


    fun fetchMyPage() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteMyPageUseCase.execute(Unit)
                    .collect {
                        event(Event.FetchMyPageValue(it))
                    }
            }.onFailure {
                when(it) {
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

    private fun event(event: Event) {
        viewModelScope.launch {
            _myPageViewEffect.emit(event)
            myPageViewEffect = _myPageViewEffect.asEventFlow()
        }
    }

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {
        TODO("Not yet implemented")
    }

    sealed class Event {
        data class FetchMyPageValue(val myPageEntity: MyPageEntity): Event()
        object NullPointException: Event()
        object UnAuthorizedTokenException: Event()
        object CannotConnectException: Event()
        object TooManyRequestException: Event()
        object InternalServerException: Event()
        object UnknownException: Event()
    }
}
