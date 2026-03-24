package team.aliens.dms.android.feature.editpassword.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class CheckPasswordViewModel @Inject constructor(
    val userRepository: UserRepository
): BaseStateViewModel<CheckPasswordState, CheckPasswordSideEffect>(CheckPasswordState()) {

    internal fun checkPassword() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            userRepository.comparePassword(
                password = uiState.value.currentPassword,
            ).onSuccess {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(CheckPasswordSideEffect.SuccessCheckPassword)
            }.onFailure {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(CheckPasswordSideEffect.FailCheckPassword("비밀번호가 일치하지 않아요"))
            }
        }
    }

    internal fun setPassword(password: String) {
        setState { it.copy(currentPassword = password) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        with(uiState.value) {
            val isSignInValueNotBlank = currentPassword.isNotBlank()
            it.copy(buttonEnabled = isSignInValueNotBlank)
        }
    }
}

data class CheckPasswordState(
    val currentPassword: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class CheckPasswordSideEffect {
    data object SuccessCheckPassword : CheckPasswordSideEffect()
    data class FailCheckPassword(val message: String) : CheckPasswordSideEffect()
}
