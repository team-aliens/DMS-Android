package com.example.dms_android.viewmodel.auth.register

import androidx.lifecycle.viewModelScope
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.ConflictException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.usecase.auth.RemoteSignUpUseCase
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.SignUpEvent
import com.example.dms_android.feature.register.state.SignUpState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
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
                SignUpEvent.SignUpSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> SignUpEvent.BadRequestException
                    is UnauthorizedException -> SignUpEvent.UnAuthorizedException
                    is NotFoundException -> SignUpEvent.NotFoundException
                    is TooManyRequestException -> SignUpEvent.TooManyRequestsException
                    is ConflictException -> SignUpEvent.ConflictException
                    is ServerException -> SignUpEvent.InternalServerException
                    else -> SignUpEvent.UnKnownException
                }
            }
        }
    }

    override val initialState: SignUpState
        get() = SignUpState.initial()


    override fun reduceEvent(oldState: SignUpState, event: SignUpEvent) {
        when (event) {
            is SignUpEvent.InputAccountId -> {
                setState(oldState.copy(accountId = event.accountId))
            }

            is SignUpEvent.InputPassword -> {
                setState(oldState.copy(password = event.password))
            }

            is SignUpEvent.InputAuthCode -> {
                setState(oldState.copy(authCode = event.authCode))
            }

            is SignUpEvent.InputClassRoom -> {
                setState(oldState.copy(classRoom = event.classRoom))
            }

            is SignUpEvent.InputEmail -> {
                setState(oldState.copy(email = event.email))
            }

            is SignUpEvent.InputGrade -> {
                setState(oldState.copy(grade = event.grade))
            }

            is SignUpEvent.InputNumber -> {
                setState(oldState.copy(number = event.number))
            }

            is SignUpEvent.InputSchoolCode -> {
                setState(oldState.copy(schoolCode = event.schoolCode))
            }

            is SignUpEvent.InputProfileImageUrl -> {
                setState(oldState.copy(profileImageUrl = event.profileImageUrl))
            }

            is SignUpEvent.InputSchoolAnswer -> {
                setState(oldState.copy(schoolAnswer = event.schoolAnswer))
            }

            else -> {}
        }
    }
}