package com.example.dms_android.viewmodel.auth.register

import androidx.lifecycle.viewModelScope
import com.example.domain.exception.BadRequestException
import com.example.domain.exception.NotFoundException
import com.example.domain.exception.ServerException
import com.example.domain.exception.TooManyRequestException
import com.example.domain.exception.UnauthorizedException
import com.example.domain.usecase.students.RemoteSignUpUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.SignUpEvent
import com.example.dms_android.feature.register.event.email.RegisterEmailEvent
import com.example.dms_android.feature.register.state.SignUpState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import com.example.domain.exception.ConflictException
import com.example.domain.param.RegisterParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val remoteSignUpUseCase: RemoteSignUpUseCase
) : BaseViewModel<SignUpState, SignUpEvent>() {

    private val parameter =
        RegisterParam(
            schoolCode = "",
            schoolAnswer = "",
            email = "",
            authCode = "",
            grade = 0,
            classRoom = 0,
            number = 0,
            accountId = "",
            password = "",
            profileImageUrl = "",
        )

    private val _signUpEvent = MutableEventFlow<SignUpEvent>()
    val signUpViewEvent = _signUpEvent.asEventFlow()

    fun signUp() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteSignUpUseCase.execute(parameter)
            }.onSuccess {
                event(SignUpEvent.SignUpSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(SignUpEvent.BadRequestException)
                    is UnauthorizedException -> event(SignUpEvent.UnAuthorizedException)
                    is NotFoundException -> event(SignUpEvent.NotFoundException)
                    is TooManyRequestException -> event(SignUpEvent.TooManyRequestsException)
                    is ConflictException -> event(SignUpEvent.ConflictException)
                    is ServerException -> event(SignUpEvent.InternalServerException)
                    else -> event(SignUpEvent.UnKnownException)
                }
            }
        }
    }

    private fun event(event: SignUpEvent) {
        viewModelScope.launch {
            _signUpEvent.emit(event)
        }
    }

    override val initialState: SignUpState
        get() = SignUpState.initial()


    override fun reduceEvent(oldState: SignUpState, event: SignUpEvent) {
        //TODO
    }
}