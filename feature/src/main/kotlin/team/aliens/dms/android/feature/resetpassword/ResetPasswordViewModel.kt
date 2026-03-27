package team.aliens.dms.android.feature.resetpassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.network.exception.NotFoundException
import team.aliens.dms.android.core.network.exception.TooManyRequestsException
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.feature.resetpassword.model.ResetPasswordTextFieldError
import team.aliens.dms.android.shared.validator.checkIfPasswordValid
import javax.inject.Inject

internal const val EMAIL_VERIFICATION_CODE_LENGTH = 6

internal enum class ResetPasswordStep {
    InputId,
    InputUserInfo,
    InputEmailVerificationCode,
    InputNewPassword,
}

@HiltViewModel
internal class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<ResetPasswordState, ResetPasswordSideEffect>(ResetPasswordState()) {

    private fun checkIdExists() = viewModelScope.launch {
        setState { it.copy(isLoading = true) }
        authRepository.checkIdExists(accountId = uiState.value.accountId)
            .onSuccess { response ->
                setState { it.copy(hashEmail = response.email) }
                sendEffect(ResetPasswordSideEffect.MoveToNext)
            }.onFailure { exception ->
                when (exception) {
                    is NotFoundException -> sendEffect(ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar)
                    else -> sendEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
                }
            }
        setState { it.copy(isLoading = false) }
    }

    private fun sendEmailVerificationCode() = viewModelScope.launch {
        setState {
            it.copy(
                isLoading = true,
                emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.None,
            )
        }
        authRepository.sendEmailVerificationCode(
            email = uiState.value.email,
            type = EmailVerificationType.PASSWORD,
        ).onSuccess {
            sendEffect(ResetPasswordSideEffect.MoveToNext)
            sendEffect(ResetPasswordSideEffect.ShowSendEmailSuccessSnackBar)
        }.onFailure { exception ->
            when (exception) {
                is NotFoundException -> sendEffect(ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar)
                is TooManyRequestsException -> sendEffect(ResetPasswordSideEffect.ShowTooManyRequestSnackBar)
                else -> sendEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
            }
        }
        setState { it.copy(isLoading = false) }
    }

    internal fun resendEmailVerificationCode() = viewModelScope.launch {
        setState {
            it.copy(
                isResendLoading = true,
                emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.None,
            )
        }
        authRepository.sendEmailVerificationCode(
            email = uiState.value.email,
            type = EmailVerificationType.PASSWORD,
        ).onSuccess {
            setState { it.copy(emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.None) }
            sendEffect(ResetPasswordSideEffect.ResetCountDownTimer)
            sendEffect(ResetPasswordSideEffect.ShowSendEmailSuccessSnackBar)
        }.onFailure { exception ->
            when (exception) {
                is NotFoundException -> sendEffect(ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar)
                else -> sendEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
            }
        }
        setState { it.copy(isResendLoading = false) }
    }

    private fun checkEmailVerificationCode() = viewModelScope.launch {
        setState {
            it.copy(
                isLoading = true,
                emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.None,
            )
        }
        authRepository.checkEmailVerificationCode(
            email = uiState.value.email,
            code = uiState.value.emailVerificationCode,
            type = EmailVerificationType.PASSWORD,
        ).onSuccess {
            sendEffect(ResetPasswordSideEffect.MoveToNext)
        }.onFailure { exception ->
            when (exception) {
                is NotFoundException -> {
                    setState { it.copy(emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.InvalidEmailVerificationCode()) }
                }
                else -> sendEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
            }
        }
        setState { it.copy(isLoading = false) }
    }

    private fun resetPassword() = viewModelScope.launch {
        setState { it.copy(isLoading = true) }
        studentRepository.editPassword(
            accountId = uiState.value.accountId,
            studentName = uiState.value.name,
            email = uiState.value.email,
            emailVerificationCode = uiState.value.emailVerificationCode,
            newPassword = uiState.value.password,
        ).onSuccess {
            sendEffect(ResetPasswordSideEffect.ShowPasswordResetSuccessSnackBar)
            sendEffect(ResetPasswordSideEffect.NavigateUp)
        }.onFailure {
            sendEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
        }
        setState { it.copy(isLoading = false) }
    }

    internal fun moveNext(step: ResetPasswordStep?) {
        if (step == null) return
        when (step) {
            ResetPasswordStep.InputId -> checkIdExists()
            ResetPasswordStep.InputUserInfo -> sendEmailVerificationCode()
            ResetPasswordStep.InputEmailVerificationCode -> checkEmailVerificationCode()
            ResetPasswordStep.InputNewPassword -> resetPassword()
        }
    }

    internal fun onStepChanged(step: ResetPasswordStep) {
        when (step) {
            ResetPasswordStep.InputId -> {
                setState { it.copy(buttonEnabled = it.accountId.isNotEmpty()) }
            }
            ResetPasswordStep.InputUserInfo -> {
                setState { it.copy(buttonEnabled = it.name.isNotEmpty() && it.email.isNotEmpty()) }
            }
            ResetPasswordStep.InputEmailVerificationCode -> {
                setState { it.copy(buttonEnabled = it.emailVerificationCode.isNotEmpty()) }
            }
            ResetPasswordStep.InputNewPassword -> {
                val password = uiState.value.password
                val confirmPassword = uiState.value.confirmPassword
                setState { it.copy(buttonEnabled = password.isNotEmpty() && password == confirmPassword) }
            }
        }
    }

    internal fun setEmailVerificationTimerFinished(isFinished: Boolean) {
        setState { it.copy(isEmailVerificationTimerFinished = isFinished) }
        if (isFinished) {
            setState {
                it.copy(
                    emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.EmailVerificationCodeTimeExpired(),
                    buttonEnabled = false,
                )
            }
        }
    }

    internal fun setAccountId(accountId: String) {
        setState { it.copy(accountId = accountId, buttonEnabled = accountId.isNotEmpty()) }
    }

    internal fun setUserInfo(
        name: String? = null,
        email: String? = null,
    ) {
        val nextName = name ?: uiState.value.name
        val nextEmail = email ?: uiState.value.email
        val buttonEnabled = nextName.isNotEmpty() && nextEmail.isNotEmpty()
        setState {
            it.copy(
                name = nextName,
                email = nextEmail,
                buttonEnabled = buttonEnabled,
            )
        }
    }

    internal fun setEmailVerificationCode(emailVerificationCode: String) {
        val buttonEnabled = isEmailVerificationButtonEnabled(emailVerificationCode = emailVerificationCode)
        setState { it.copy(emailVerificationCode = emailVerificationCode, buttonEnabled = buttonEnabled) }
    }

    internal fun setPasswordInput(
        password: String? = null,
        confirmPassword: String? = null,
    ) {
        setState {
            it.copy(
                password = password ?: it.password,
                confirmPassword = confirmPassword ?: it.confirmPassword,
            )
        }
        passwordValidation()
    }

    private fun passwordValidation() {
        val password = uiState.value.password
        val confirmPassword = uiState.value.confirmPassword
        val isValidPassword = checkIfPasswordValid(password)
        val isMatchPassword = password == confirmPassword
        val buttonEnabled =
            isValidPassword && isMatchPassword && password.isNotEmpty() && confirmPassword.isNotEmpty()
        val passwordTextFieldError = if (isValidPassword || password.isEmpty()) {
            ResetPasswordTextFieldError.None
        } else {
            ResetPasswordTextFieldError.InvalidPasswordFormat()
        }
        val confirmPasswordTextFieldError = if (isMatchPassword || confirmPassword.isEmpty()) {
            ResetPasswordTextFieldError.None
        } else {
            ResetPasswordTextFieldError.PasswordMismatch()
        }
        setState {
            it.copy(
                passwordTextFieldError = passwordTextFieldError,
                confirmPasswordTextFieldError = confirmPasswordTextFieldError,
                buttonEnabled = buttonEnabled,
            )
        }
    }

    private fun isEmailVerificationButtonEnabled(emailVerificationCode: String): Boolean =
        emailVerificationCode.length == EMAIL_VERIFICATION_CODE_LENGTH && !uiState.value.isEmailVerificationTimerFinished
}

internal data class ResetPasswordState(
    val accountId: String = "",
    val name: String = "",
    val hashEmail: String = "",
    val email: String = "",
    val emailVerificationCode: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isResendLoading: Boolean = false,
    val isEmailVerificationTimerFinished: Boolean = false,
    val emailVerificationCodeTextFieldError: ResetPasswordTextFieldError = ResetPasswordTextFieldError.None,
    val passwordTextFieldError: ResetPasswordTextFieldError = ResetPasswordTextFieldError.None,
    val confirmPasswordTextFieldError: ResetPasswordTextFieldError = ResetPasswordTextFieldError.None,
)

internal sealed interface ResetPasswordSideEffect {
    data object NavigateUp : ResetPasswordSideEffect
    data object ResetCountDownTimer : ResetPasswordSideEffect
    data object MoveToNext : ResetPasswordSideEffect
    data object ShowSendEmailSuccessSnackBar : ResetPasswordSideEffect
    data object ShowNotFoundAccountIdSnackBar : ResetPasswordSideEffect
    data object ShowTooManyRequestSnackBar : ResetPasswordSideEffect
    data object ShowServerErrorSnackBar : ResetPasswordSideEffect
    data object ShowPasswordResetSuccessSnackBar : ResetPasswordSideEffect
}
