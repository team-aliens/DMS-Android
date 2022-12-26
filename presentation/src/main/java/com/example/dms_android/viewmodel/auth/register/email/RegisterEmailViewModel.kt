package com.example.dms_android.viewmodel.auth.register.email

import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.user.RemoteCheckEmailUseCase
import com.example.domain.usecase.user.RemoteRequestEmailCodeUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.email.RegisterEmailEvent
import com.example.dms_android.feature.register.state.email.RegisterEmailState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.enums.EmailType
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.param.CheckEmailCodeParam
import com.example.domain.param.RequestEmailCodeParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val remoteRequestEmailCodeUseCase: RemoteRequestEmailCodeUseCase,
    private val remoteCheckEmailUseCase: RemoteCheckEmailUseCase,
) : BaseViewModel<RegisterEmailState, RegisterEmailEvent>() {

    private val _registerEmailEvent = MutableEventFlow<RegisterEmailEvent>()
    val registerEmailEvent = _registerEmailEvent.asEventFlow()

    fun requestEmailCode(email: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteRequestEmailCodeUseCase.execute(
                    RequestEmailCodeParam(
                        email = email,
                        type = EmailType.SIGNUP
                    )
                )
            }.onSuccess {
                event(RegisterEmailEvent.SendEmailSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(RegisterEmailEvent.BadRequestException)
                    is TooManyRequestException -> event(RegisterEmailEvent.TooManyRequestsException)
                    is ServerException -> event(RegisterEmailEvent.InternalServerException)
                    else -> event(RegisterEmailEvent.UnKnownException)
                }
            }
        }
    }

    fun checkEmailCode(email: String, authCode: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteCheckEmailUseCase.execute(
                    CheckEmailCodeParam(
                        email = email,
                        type = EmailType.SIGNUP,
                        authCode = authCode,
                    )
                )
            }.onSuccess {
                event(RegisterEmailEvent.CheckEmailSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(RegisterEmailEvent.BadRequestException)
                    is UnauthorizedException -> event(RegisterEmailEvent.CheckEmailUnauthorized)
                    is NotFoundException -> event(RegisterEmailEvent.CheckEmailNotFound)
                    is TooManyRequestException -> event(RegisterEmailEvent.TooManyRequestsException)
                    is ServerException -> event(RegisterEmailEvent.InternalServerException)
                    else -> event(RegisterEmailEvent.InternalServerException)
                }
            }
        }
    }

    private fun event(event: RegisterEmailEvent) {
        viewModelScope.launch {
            _registerEmailEvent.emit(event)
        }
    }

    override val initialState: RegisterEmailState
        get() = RegisterEmailState.initial()

    override fun reduceEvent(oldState: RegisterEmailState, event: RegisterEmailEvent) {
        TODO("Not yet implemented")
    }
}