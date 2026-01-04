package team.aliens.dms.android.feature.resetpassword.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    val userRepository: UserRepository
): BaseStateViewModel<ResetPasswordState, ResetPasswordSideEffect>(ResetPasswordState()) {

    internal fun setNewPassword(password: String) {
        setState { it.copy(newPassword = password) }
        setButtonEnabled()
    }

    internal fun setCheckNewPassword(password: String) {
        setState { it.copy(checkNewPassword = password) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState { // TODO :: textfield description message 처리 고려 (타입에 따른 메세지 분기처리, 한 프로퍼티로 처리 등등)
        with(uiState.value) {
            val isSignInValueNotBlank = newPassword.isNotBlank() && checkNewPassword.isNotBlank() && newPassword == checkNewPassword
            it.copy(buttonEnabled = isSignInValueNotBlank)
        }
    }

    internal fun resetPassword() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            userRepository.comparePassword(
                password = uiState.value.newPassword,
            ).onSuccess {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(ResetPasswordSideEffect.SuccessResetPassword)
            }.onFailure {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(ResetPasswordSideEffect.FailResetPassword("비밀번호 변경을 실패했어요"))
            }
        }
    }
}

data class ResetPasswordState(
    val newPassword: String = "",
    val checkNewPassword: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class ResetPasswordSideEffect { // TODO :: 더 좋은 선택지 고려
    data object SuccessResetPassword : ResetPasswordSideEffect()
    data class FailResetPassword(val message: String) : ResetPasswordSideEffect()
}
