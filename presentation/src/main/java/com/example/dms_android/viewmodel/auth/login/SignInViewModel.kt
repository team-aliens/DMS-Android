package com.example.dms_android.viewmodel.auth.login

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.ConflictException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.param.LoginParam
import com.example.auth_domain.usecase.auth.RemoteSignInUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.auth.login.SignInEvent
import com.example.dms_android.feature.auth.login.SignInState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val remoteSignInUseCase: RemoteSignInUseCase
) : BaseViewModel<SignInState, SignInEvent>() {

    private val parameter =
        LoginParam(id = state.value.id, password = state.value.password)

    fun setId(id: String) {
        sendEvent(SignInEvent.InputId(id))
    }

    fun setPassword(password: String) {
        sendEvent(SignInEvent.InputPassword(password))
    }

    override val initialState: SignInState
        get() = SignInState.initial()

    private val _signInEvent = MutableEventFlow<SignInEvent>()
    val signInViewEvent = _signInEvent.asEventFlow()

    fun signIn() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.Default) {
                    remoteSignInUseCase.execute(parameter)
                }
            }.onSuccess{
                SignInEvent.SignInSuccess
            }.onFailure {
                when(it) {
                    is BadRequestException -> SignInEvent.BadRequestException
                    is UnauthorizedException -> SignInEvent.UnAuthorizedException
                    is NotFoundException -> SignInEvent.NotFoundException
                    is TooManyRequestException -> SignInEvent.TooManyRequestException
                    is ServerException -> SignInEvent.InternalServerException
                    else -> SignInEvent.UnKnownException
                }
            }
        }
    }

    override fun reduceEvent(oldState: SignInState, event: SignInEvent) {
        when(event) {
            is SignInEvent.InputId -> {
                setState(oldState.copy(id = event.id))
            }
            is SignInEvent.InputPassword -> {
                setState(oldState.copy(password = event.password))
            }
            else -> {}
        }
    }
}