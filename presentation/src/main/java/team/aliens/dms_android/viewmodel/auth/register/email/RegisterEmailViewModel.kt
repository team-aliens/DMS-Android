package team.aliens.dms_android.viewmodel.auth.register.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain.enums.EmailType
import team.aliens.domain.exception.*
import team.aliens.domain.param.CheckEmailCodeParam
import team.aliens.domain.param.RequestEmailCodeParam
import team.aliens.domain.usecase.user.RemoteCheckEmailUseCase
import team.aliens.domain.usecase.user.RemoteRequestEmailCodeUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val remoteRequestEmailCodeUseCase: RemoteRequestEmailCodeUseCase,
    private val remoteCheckEmailUseCase: RemoteCheckEmailUseCase,
) : ViewModel() {

    private val _registerEmailEvent = MutableEventFlow<RegisterEmailEvent>()
    val registerEmailEvent = _registerEmailEvent.asEventFlow()

    fun requestEmailCode(email: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteRequestEmailCodeUseCase.execute(RequestEmailCodeParam(email = email,
                    type = EmailType.SIGNUP))
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
                remoteCheckEmailUseCase.execute(CheckEmailCodeParam(
                    email = email,
                    type = EmailType.SIGNUP,
                    authCode = authCode,
                ))
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
}