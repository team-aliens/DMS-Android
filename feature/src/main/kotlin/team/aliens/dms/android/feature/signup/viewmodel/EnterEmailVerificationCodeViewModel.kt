package team.aliens.dms.android.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.model.SignUpTextFieldError
import javax.inject.Inject

private const val EMAIL_VERIFICATION_CODE_LENGTH = 6

@HiltViewModel
internal class EnterEmailVerificationCodeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseStateViewModel<EnterEmailVerificationCodeState, EnterEmailVerificationCodeSideEffect>(
    EnterEmailVerificationCodeState()
) {
    private lateinit var currentSignUpData: SignUpData

    internal fun initialize(signUpData: SignUpData) {
        currentSignUpData = signUpData
        setState { it.copy(email = signUpData.email) }
        sendEmailVerificationCode()
    }

    private fun sendEmailVerificationCode() = viewModelScope.launch {
        setState { it.copy(textFieldError = SignUpTextFieldError.None) }
        authRepository.sendEmailVerificationCode(
            email = currentSignUpData.email,
            type = EmailVerificationType.SIGNUP,
        ).onFailure {
            sendEffect(EnterEmailVerificationCodeSideEffect.ShowSendErrorSnackBar)
        }
    }

    internal fun resendEmailVerificationCode() = viewModelScope.launch {
        setState { it.copy(isResendLoading = true, textFieldError = SignUpTextFieldError.None) }
        authRepository.sendEmailVerificationCode(
            email = uiState.value.email,
            type = EmailVerificationType.SIGNUP,
        ).onSuccess {
            setState { it.copy(textFieldError = SignUpTextFieldError.None) }
            sendEffect(EnterEmailVerificationCodeSideEffect.ResetCountDownTimer)
        }.onFailure {
            sendEffect(EnterEmailVerificationCodeSideEffect.ShowSendErrorSnackBar)
        }
        setState { it.copy(isResendLoading = false) }
    }

    internal fun setEmailVerificationCode(code: String) {
        setState { it.copy(emailVerificationCode = code) }
        setButtonEnabled()
    }

    internal fun setTimerFinished(isFinished: Boolean) {
        setState { it.copy(isTimerFinished = isFinished) }
        if (isFinished) {
            setState {
                it.copy(
                    buttonEnabled = false,
                    textFieldError = SignUpTextFieldError.EmailVerificationCodeTimeExpired(),
                )
            }
        }
    }

    private fun setButtonEnabled() = setState {
        it.copy(buttonEnabled = it.emailVerificationCode.length == EMAIL_VERIFICATION_CODE_LENGTH && !it.isTimerFinished)
    }

    internal fun onNextClick() = viewModelScope.launch {
        setState { it.copy(isLoading = true, buttonEnabled = false) }
        authRepository.checkEmailVerificationCode(
            email = currentSignUpData.email,
            code = uiState.value.emailVerificationCode,
            type = EmailVerificationType.SIGNUP,
        ).onSuccess {
            sendEffect(
                EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber(
                    signUpData = currentSignUpData.copy(authCode = uiState.value.emailVerificationCode)
                )
            )
        }.onFailure {
            setState { it.copy(textFieldError = SignUpTextFieldError.InvalidEmailVerificationCode()) }
        }
        setState { it.copy(isLoading = false) }
        setButtonEnabled()
    }
}

internal data class EnterEmailVerificationCodeState(
    val email: String = "",
    val emailVerificationCode: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isResendLoading: Boolean = false,
    val isTimerFinished: Boolean = false,
    val textFieldError: SignUpTextFieldError = SignUpTextFieldError.None,
)

internal sealed interface EnterEmailVerificationCodeSideEffect {
    data class MoveToEnterStudentNumber(val signUpData: SignUpData) : EnterEmailVerificationCodeSideEffect
    data object ShowSendErrorSnackBar : EnterEmailVerificationCodeSideEffect
    data object ResetCountDownTimer : EnterEmailVerificationCodeSideEffect
}
