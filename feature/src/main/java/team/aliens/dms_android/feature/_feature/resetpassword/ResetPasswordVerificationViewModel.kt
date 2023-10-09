package team.aliens.dms_android.feature._feature.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.util.MutableEventFlow
import team.aliens.dms_android.feature.util.asEventFlow
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.domain.model.student.CheckEmailDuplicationInput
import team.aliens.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.domain.usecase.student.CheckEmailDuplicationUseCase
import javax.inject.Inject

@HiltViewModel
class ResetPasswordVerificationViewModel @Inject constructor(
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,
    private val checkEmailVerificationCodeUseCase: CheckEmailVerificationCodeUseCase,
    private val checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
) : ViewModel() {

    private val _resetPasswordVerificationEvent = MutableEventFlow<ResetPasswordVerificationEvent>()
    val registerEmailEvent = _resetPasswordVerificationEvent.asEventFlow()

    fun requestEmailCode(
        email: String,
        type: EmailVerificationType,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                sendEmailVerificationCodeUseCase(
                    sendEmailVerificationCodeInput = SendEmailVerificationCodeInput(
                        email = email,
                        type = type,
                    ),
                )
            }.onSuccess {
                event(ResetPasswordVerificationEvent.SendEmailSuccess)
            }.onFailure {
                // fixme 추후에 리팩토링 필요
                when (it) {
                    else -> event(ResetPasswordVerificationEvent.UnKnownException)
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
                    checkEmailVerificationCodeInput = CheckEmailVerificationCodeInput(
                        email = email,
                        type = type,
                        authCode = authCode,
                    ),
                )
            }.onSuccess {
                event(ResetPasswordVerificationEvent.CheckEmailSuccess)
            }.onFailure {
                // fixme 추후에 리팩토링 필요
                when (it) {
                    else -> event(ResetPasswordVerificationEvent.InternalServerException)
                }
            }
        }
    }

    internal fun checkEmailDuplicate(
        email: String,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                checkEmailDuplicationUseCase(
                    checkEmailDuplicationInput = CheckEmailDuplicationInput(
                        email = email,
                    ),
                )
            }.onSuccess {
                event(ResetPasswordVerificationEvent.AllowEmail)
            }.onFailure {
                event(getEventFromThrowable(it))
            }
        }
    }

    private fun event(event: ResetPasswordVerificationEvent) {
        viewModelScope.launch {
            _resetPasswordVerificationEvent.emit(event)
        }
    }
}

// fixme 추후에 리팩토링 필요
private fun getEventFromThrowable(
    throwable: Throwable?,
): ResetPasswordVerificationEvent =
    when (throwable) {
        else -> {
            ResetPasswordVerificationEvent.UnKnownException
        }
    }
