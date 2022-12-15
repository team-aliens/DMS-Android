package com.example.dms_android.viewmodel.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.auth_domain.entity.AuthInfoEntity
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.exception.UnknownException
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.usecase.user.RemoteSignInUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.auth.login.SignInEvent
import com.example.dms_android.feature.auth.login.SignInState
import com.example.dms_android.feature.auth.login.SignInViewEffect
import com.example.dms_android.util.EventFlow
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val remoteSignInUseCase: RemoteSignInUseCase,
) : BaseViewModel<SignInState, SignInEvent>() {

    private var parameter =
        LoginParam(id = state.value.id, password = state.value.password)

    fun setId(id: String) {
        sendEvent(SignInEvent.InputId(id))
    }

    fun setPassword(password: String) {
        sendEvent(SignInEvent.InputPassword(password))
    }

    override val initialState: SignInState
        get() = SignInState.initial()

    private val _signInViewEffect = MutableEventFlow<Event>()
    var signInViewEffect = _signInViewEffect.asEventFlow()


    fun signIn() {
        parameter =
            LoginParam(id = state.value.id, password = state.value.password)
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSignInUseCase.execute(parameter)
            }.onSuccess {
                Log.d("123", "1234")
                event(Event.SignInSuccess)
            }.onFailure {
                Log.d("123", "qwer")
                when(it) {
                    is BadRequestException -> event(Event.WrongRequest)
                    is UnauthorizedException -> event(Event.NotCorrectPassword)
                    is NotFoundException -> event(Event.UserNotFound)
                    is TooManyRequestException -> event(Event.TooManyRequest)
                    is ServerException -> event(Event.ServerException)
                    is UnknownException -> event(Event.UnKnownException)
                }
            }
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _signInViewEffect.emit(event)
            signInViewEffect = _signInViewEffect.asEventFlow()
        }
    }

    override fun reduceEvent(oldState: SignInState, event: SignInEvent) {
        when (event) {
            is SignInEvent.InputId -> {
                setState(oldState.copy(id = event.id))
            }
            is SignInEvent.InputPassword -> {
                setState(oldState.copy(password = event.password))
            }
            else -> {}
        }
    }

    sealed class Event() {
        object SignInSuccess : Event()
        object WrongRequest : Event()
        object NotCorrectPassword : Event()
        object UserNotFound : Event()
        object TooManyRequest : Event()
        object ServerException : Event()
        object UnKnownException : Event()
    }
}
