package team.aliens.dms.android.feature.resetpassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.repository.AuthRepository
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.shared.validator.checkIfEmailValid
import team.aliens.dms.android.shared.validator.checkIfPasswordValid
import java.util.UUID
import javax.inject.Inject

const val SEARCH_DEBOUNCE_MILLIS = 1000L

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val authRepository: AuthRepository,
) : BaseMviViewModel<ResetPasswordUiState, ResetPasswordIntent, ResetPasswordSideEffect>(
    initialState = ResetPasswordUiState.initial(),
) {
    /*디자인에서 처음 본인인증할때 아이디만을 사용해서 "아이디 존재 여부(비밀번호 재설정)"이라는 Api로 이에 해당하는 Email를 받습니다.
       그다음 아이디를 입력 받은 다음에 "이메일 검증이라는 Api를 사용하여 이메일과 아이디를 서버에 보낸뒤 이 값들이 정보와 일치하는지 검사합니다."
       검사에서 가능이 뜨게 된다면 "이메일 인증번호 보내기 APi"를 사용해서 사용자 이메일에 이메일을 발송합니다.
       그리고 이메일 인증번호 확인 Api를 사용하여 인증을 완료하고 Students의 비밀번호 재설정 Api를 사용하여 재설정합니다.*/

    init {
        debounceName()
    }

    override fun processIntent(intent: ResetPasswordIntent) {
        when (intent) {
            ResetPasswordIntent.SetPassword -> resetPassword()
            is ResetPasswordIntent.UpdateNewPassword -> this.updateNewPassword(value = intent.value)
            is ResetPasswordIntent.UpdateEmailVerificationCode -> updateEmailVerificationCode(value = intent.value)
            ResetPasswordIntent.CheckEmailVerificationCode -> checkEmailVerificationCode()
            ResetPasswordIntent.ResetEmailVerificationSession -> resetEmailVerificationSession()
            is ResetPasswordIntent.UpdateAccountId -> this.updateAccountId(value = intent.value)
            is ResetPasswordIntent.CheckAccountId -> checkIdExists()
            is ResetPasswordIntent.UpdateStudentName -> this.updateStudentName(value = intent.value)
            is ResetPasswordIntent.UpdateEmail -> this.updateEmail(value = intent.value)
            is ResetPasswordIntent.SendEmailVerificationCode -> this.sendEmailVerificationCode(email = intent.value)
            is ResetPasswordIntent.UpdateNewPasswordRepeat -> updateNewPasswordRepeat(value = intent.value)
        }
    }

    @OptIn(FlowPreview::class)
    private fun debounceName() {
        viewModelScope.launch {
            stateFlow.map { it.accountId }.distinctUntilChanged().debounce(SEARCH_DEBOUNCE_MILLIS).collect {
                if (it.isNotBlank()) {
                    checkIdExists()
                }
            }
        }
    }

    private fun resetPassword() = viewModelScope.launch(Dispatchers.IO) {
        val capturedState = stateFlow.value
        if (capturedState.newPassword != capturedState.newPasswordRepeat) {
            postSideEffect(ResetPasswordSideEffect.PasswordMismatch)
            return@launch
        }

        if (!checkIfPasswordValid(capturedState.newPassword)) {
            postSideEffect(ResetPasswordSideEffect.PasswordFormatError)
            return@launch
        }

        runCatching {
            studentRepository.resetPassword(
                accountId = capturedState.accountId,
                studentName = capturedState.studentName,
                email = capturedState.email,
                emailVerificationCode = capturedState.emailVerificationCode,
                newPassword = capturedState.newPassword,
            )
        }.onSuccess {
            postSideEffect(ResetPasswordSideEffect.PasswordReset)
        }.onFailure {
            it.printStackTrace()
            postSideEffect(ResetPasswordSideEffect.PasswordFormatError)
        }
    }

    private fun checkIdExists() = viewModelScope.launch(Dispatchers.IO) {
        val capturedState = stateFlow.value

        runCatching {
            authRepository.checkIdExists(
                accountId = capturedState.accountId,
            )
        }.onSuccess {
            reduce(
                newState = stateFlow.value.copy(
                    hashedEmail = it,
                    isAccountIdError = false,
                ),
            )
            postSideEffect(ResetPasswordSideEffect.AccountIdExists)
        }.onFailure {
            reduce(
                newState = stateFlow.value.copy(
                    isAccountIdError = true,
                ),
            )
            postSideEffect(ResetPasswordSideEffect.AccountIdNotExists)
        }
    }

    private fun sendEmailVerificationCode(email: String) =
        viewModelScope.launch(Dispatchers.IO) {
            if (!checkIfEmailValid(email)) {
                postSideEffect(ResetPasswordSideEffect.InvalidEmailFormat)
                return@launch
            }
            runCatching {
                authRepository.sendEmailVerificationCode(
                    email = email,
                    type = EmailVerificationType.PASSWORD,
                )
            }.onSuccess {
                postSideEffect(ResetPasswordSideEffect.SendEmailVerificationCodeSuccess)
            }.onFailure {
                postSideEffect(ResetPasswordSideEffect.EmailVerificationUserNotFound)
            }
        }

    private fun updateEmailVerificationCode(value: String) = run {
        if (value.length > EMAIL_VERIFICATION_CODE_LENGTH) {
            return@run false
        }
        reduce(newState = stateFlow.value.copy(emailVerificationCode = value))
    }

    private fun checkEmailVerificationCode() = viewModelScope.launch(Dispatchers.IO) {
        val capturedState = stateFlow.value
        runCatching {
            authRepository.checkEmailVerificationCode(
                email = capturedState.email,
                code = capturedState.emailVerificationCode,
                type = EmailVerificationType.PASSWORD,
            )
        }.onSuccess {
            postSideEffect(ResetPasswordSideEffect.EmailVerificationCodeChecked)
        }.onFailure {
            postSideEffect(ResetPasswordSideEffect.EmailVerificationCodeIncorrect)
            reduce(newState = stateFlow.value.copy(emailVerificationCode = ""))
        }
    }

    private fun resetEmailVerificationSession() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            this@ResetPasswordViewModel.sendEmailVerificationCode(email = stateFlow.value.email)
        }.onSuccess {
            postSideEffect(ResetPasswordSideEffect.EmailVerificationSessionReset)
            reduce(newState = stateFlow.value.copy(emailVerificationCode = ""))
        }.onFailure {
            postSideEffect(ResetPasswordSideEffect.EmailVerificationSessionResetFailed)
        }
    }

    private fun updateNewPassword(value: String) = reduce(
        newState = stateFlow.value.copy(
            newPassword = value,
        ),
    )

    private fun updateAccountId(value: String) = reduce(
        newState = stateFlow.value.copy(
            accountId = value,
        ),
    )

    private fun updateStudentName(value: String) = reduce(
        newState = stateFlow.value.copy(
            studentName = value,
        ),
    )

    private fun updateEmail(value: String) = reduce(
        newState = stateFlow.value.copy(
            email = value,
        ),
    )

    private fun updateNewPasswordRepeat(value: String) = reduce(
        newState = stateFlow.value.copy(
            newPasswordRepeat = value,
        ),
    )

    companion object {
        const val EMAIL_VERIFICATION_CODE_LENGTH = 6
    }
}

data class ResetPasswordUiState(
    val accountId: String,
    val studentName: String,
    val email: String,
    val emailVerificationCode: String,
    val newPassword: String,
    val newPasswordRepeat: String,
    val hashedEmail: String,
    val sessionId: UUID,
    val isAccountIdError: Boolean,
) : UiState() {
    companion object {
        fun initial() = ResetPasswordUiState(
            accountId = "",
            studentName = "",
            email = "",
            emailVerificationCode = "",
            newPassword = "",
            newPasswordRepeat = "",
            hashedEmail = "",
            sessionId = UUID.randomUUID(),
            isAccountIdError = false
        )
    }
}

sealed class ResetPasswordIntent : Intent() {
    class UpdateNewPassword(val value: String) : ResetPasswordIntent()
    class UpdateNewPasswordRepeat(val value: String) : ResetPasswordIntent()
    data object SetPassword : ResetPasswordIntent()
    class UpdateEmailVerificationCode(val value: String) : ResetPasswordIntent()
    data object CheckEmailVerificationCode : ResetPasswordIntent()
    data object ResetEmailVerificationSession : ResetPasswordIntent()
    class UpdateAccountId(val value: String) : ResetPasswordIntent()
    data object CheckAccountId : ResetPasswordIntent()
    class UpdateStudentName(val value: String) : ResetPasswordIntent()
    class UpdateEmail(val value: String) : ResetPasswordIntent()
    class SendEmailVerificationCode(val value: String) : ResetPasswordIntent()
}

sealed class ResetPasswordSideEffect : SideEffect() {
    data object PasswordMismatch : ResetPasswordSideEffect()
    data object PasswordFormatError : ResetPasswordSideEffect()
    data object PasswordReset : ResetPasswordSideEffect()
    data object InvalidPassword : ResetPasswordSideEffect()
    data object AccountIdExists : ResetPasswordSideEffect()
    data object AccountIdNotExists : ResetPasswordSideEffect()
    data object SendEmailVerificationCodeSuccess : ResetPasswordSideEffect()
    data object EmailVerificationCodeChecked : ResetPasswordSideEffect()
    data object EmailVerificationCodeIncorrect : ResetPasswordSideEffect()
    data object EmailVerificationSessionReset : ResetPasswordSideEffect()
    data object EmailVerificationSessionResetFailed : ResetPasswordSideEffect()
    data object EmailVerificationUserNotFound : ResetPasswordSideEffect()
    data object InvalidEmailFormat :  ResetPasswordSideEffect()
}
