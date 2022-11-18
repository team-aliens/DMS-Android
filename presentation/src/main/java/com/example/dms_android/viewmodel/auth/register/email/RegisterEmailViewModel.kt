package com.example.dms_android.viewmodel.auth.register.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.auth_domain.enum.EmailType
import com.example.auth_domain.exception.BadRequestException
import com.example.auth_domain.exception.NotFoundException
import com.example.auth_domain.exception.ServerException
import com.example.auth_domain.exception.TooManyRequestException
import com.example.auth_domain.exception.UnauthorizedException
import com.example.auth_domain.param.CheckEmailCodeParam
import com.example.auth_domain.param.CompareEmailParam
import com.example.auth_domain.param.RequestEmailCodeParam
import com.example.auth_domain.usecase.user.RemoteCheckEmailUseCase
import com.example.auth_domain.usecase.user.RemoteCompareEmailUseCase
import com.example.auth_domain.usecase.user.RemoteRequestEmailCodeUseCase
import com.example.dms_android.R
import com.example.dms_android.base.BaseViewModel
import com.example.dms_android.feature.register.event.email.RegisterEmailEvent
import com.example.dms_android.feature.register.state.email.RegisterEmailState
import com.example.dms_android.util.MutableEventFlow
import com.example.dms_android.util.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val remoteRequestEmailCodeUseCase: RemoteRequestEmailCodeUseCase,
    private val remoteCheckEmailUseCase: RemoteCheckEmailUseCase,
) : BaseViewModel<RegisterEmailState, RegisterEmailEvent>() {

    var _email: MutableLiveData<String> = MutableLiveData()
    var email: LiveData<String> = _email

    var _authCode: MutableLiveData<String> = MutableLiveData()
    var authCode: LiveData<String> = _authCode

    private val _registerEmailEvent = MutableEventFlow<RegisterEmailEvent>()
    val registerEmailEvent = _registerEmailEvent.asEventFlow()

    private val requestEmailParameter = RequestEmailCodeParam(
        email = email.value.toString(),
        type = EmailType.SIGNUP,
    )

    private val checkEmailParam = CheckEmailCodeParam(
        email = email.value.toString(),
        type = EmailType.SIGNUP,
        authCode = authCode.value.toString(),
    )

    fun sendEmailNumber() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteRequestEmailCodeUseCase.execute(requestEmailParameter)
            }.onSuccess {
                RegisterEmailEvent.SendEmailSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> RegisterEmailEvent.ErrorMessage(R.string.LoginBadRequest.toString())
                    is NotFoundException -> RegisterEmailEvent.ErrorMessage("찾을 수 없습니다.")
                    is TooManyRequestException -> RegisterEmailEvent.ErrorMessage(R.string.TooManyRequest.toString())
                    is ServerException -> RegisterEmailEvent.ErrorMessage(R.string.ServerException.toString())
                    else -> RegisterEmailEvent.ErrorMessage(R.string.UnKnownException.toString())
                }
            }
        }
    }

    fun checkEmailCode() {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteCheckEmailUseCase.execute(checkEmailParam)
            }.onSuccess {
                RegisterEmailEvent.CheckEmailSuccess
            }.onFailure {
                when (it) {
                    is BadRequestException -> RegisterEmailEvent.ErrorMessage(R.string.LoginBadRequest.toString())
                    is UnauthorizedException -> RegisterEmailEvent.CheckEmailUnauthorized
                    is NotFoundException -> RegisterEmailEvent.ErrorMessage(R.string.CertificationInfoNotFound.toString())
                    is TooManyRequestException -> RegisterEmailEvent.ErrorMessage(R.string.TooManyRequest.toString())
                    is ServerException -> RegisterEmailEvent.ErrorMessage(R.string.ServerException.toString())
                    else -> RegisterEmailEvent.ErrorMessage(R.string.UnKnownException.toString())
                }
            }
        }
    }

    override val initialState: RegisterEmailState
        get() = RegisterEmailState.initial()

    override fun reduceEvent(oldState: RegisterEmailState, event: RegisterEmailEvent) {
        TODO("Not yet implemented")
    }
}