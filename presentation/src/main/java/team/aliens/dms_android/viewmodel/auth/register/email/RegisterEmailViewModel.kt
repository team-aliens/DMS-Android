package team.aliens.dms_android.viewmodel.auth.register.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.register.event.email.RegisterEmailEvent
import team.aliens.dms_android.util.MutableEventFlow
import team.aliens.dms_android.util.asEventFlow
import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckEmailVerificationCodeInput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain.exception.BadRequestException
import team.aliens.domain.exception.ConflictException
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.exception.ServerException
import team.aliens.domain.exception.TooManyRequestException
import team.aliens.domain.exception.UnauthorizedException
import team.aliens.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.domain.usecase.student.CheckEmailDuplicationUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,
    private val checkEmailVerificationCodeUseCase: CheckEmailVerificationCodeUseCase,
    private val remoteCheckEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
) : ViewModel() {

    private val _registerEmailEvent = MutableEventFlow<RegisterEmailEvent>()
    val registerEmailEvent = _registerEmailEvent.asEventFlow()

    fun requestEmailCode(
        email: String,
        type: EmailVerificationType,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                sendEmailVerificationCodeUseCase(
                    SendEmailVerificationCodeInput(
                        email = email,
                        type = type,
                    ),
                )
            }.onSuccess {
                event(RegisterEmailEvent.SendEmailSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(RegisterEmailEvent.BadRequestException)
                    is NotFoundException -> event(RegisterEmailEvent.CheckEmailNotFound)
                    is ConflictException -> event(RegisterEmailEvent.ConflictException)
                    is TooManyRequestException -> event(RegisterEmailEvent.TooManyRequestsException)
                    is ServerException -> event(RegisterEmailEvent.InternalServerException)
                    else -> event(RegisterEmailEvent.UnKnownException)
                }
            }
        }
    }

    fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                checkEmailVerificationCodeUseCase(
                    CheckEmailVerificationCodeInput(
                        email = email,
                        type = type,
                        authCode = authCode,
                    ),
                )
            }.onSuccess {
                event(RegisterEmailEvent.CheckEmailSuccess)
            }.onFailure {
                when (it) {
                    is BadRequestException -> event(RegisterEmailEvent.BadRequestException)
                    is UnauthorizedException -> event(RegisterEmailEvent.CheckEmailUnauthorized)
                    is NotFoundException -> event(RegisterEmailEvent.CheckEmailNotFound)
                    is ConflictException -> event(RegisterEmailEvent.ConflictException)
                    is TooManyRequestException -> event(RegisterEmailEvent.TooManyRequestsException)
                    is ServerException -> event(RegisterEmailEvent.InternalServerException)
                    else -> event(RegisterEmailEvent.InternalServerException)
                }
            }
        }
    }

    internal fun checkEmailDuplicate(
        email: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                remoteCheckEmailDuplicationUseCase(email)
            }.onSuccess {
                event(RegisterEmailEvent.AllowEmail)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    private fun event(event: RegisterEmailEvent) {
        viewModelScope.launch {
            _registerEmailEvent.emit(event)
        }
    }
}

private fun getEventFromThrowable(
    throwable: Throwable?,
): RegisterEmailEvent =
    when (throwable) {
        is BadRequestException -> {
            RegisterEmailEvent.BadRequestException
        }
        is ConflictException -> {
            RegisterEmailEvent.ConflictException
        }
        is TooManyRequestException -> {
            RegisterEmailEvent.TooManyRequestsException
        }
        is ServerException -> {
            RegisterEmailEvent.InternalServerException
        }
        else -> {
            RegisterEmailEvent.UnKnownException
        }
    }