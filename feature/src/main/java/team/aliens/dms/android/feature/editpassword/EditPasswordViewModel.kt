package team.aliens.dms.android.feature.editpassword

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class EditPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<EditPasswordUiState, EditPasswordIntent, EditPasswordSideEffect>(
    initialState = EditPasswordUiState.initial(),
) {
    override fun processIntent(intent: EditPasswordIntent) {
        when (intent) {
            is EditPasswordIntent.UpdateCurrentPassword -> this.updateCurrentPassword(value = intent.value)
            EditPasswordIntent.ConfirmPassword -> this.confirmPassword()
            is EditPasswordIntent.UpdateNewPassword -> this.updateNewPassword(value = intent.value)
            is EditPasswordIntent.UpdateNewPasswordRepeat -> this.updateNewPasswordRepeat(value = intent.value)
            EditPasswordIntent.SetPassword -> this.setPassword()
        }
    }

    private fun updateCurrentPassword(value: String) = reduce(
        newState = stateFlow.value.copy(
            currentPassword = value,
        ),
    )

    private fun confirmPassword() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            userRepository.comparePassword(password = stateFlow.value.currentPassword)
        }.onSuccess {
            postSideEffect(EditPasswordSideEffect.ConfirmPasswordPasswordConfirmed)
        }.onFailure {
            // FIXME: Handle Throwable
            postSideEffect(EditPasswordSideEffect.ConfirmPasswordPasswordMismatch)
        }
    }

    private fun updateNewPassword(value: String) = reduce(
        newState = stateFlow.value.copy(
            newPassword = value,
        ),
    )

    private fun updateNewPasswordRepeat(value: String) = reduce(
        newState = stateFlow.value.copy(
            newPasswordRepeat = value,
        ),
    )

    private fun setPassword() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            userRepository.editPassword(
                currentPassword = stateFlow.value.currentPassword,
                newPassword = stateFlow.value.newPassword,
            )
        }.onSuccess {
            // FIXME: Handle Throwable
            postSideEffect(EditPasswordSideEffect.SetPasswordPasswordEdited)
        }
    }
}

data class EditPasswordUiState(
    val currentPassword: String,
    val newPassword: String,
    val newPasswordRepeat: String,
) : UiState() {
    companion object {
        fun initial() = EditPasswordUiState(
            currentPassword = "",
            newPassword = "",
            newPasswordRepeat = "",
        )
    }
}

sealed class EditPasswordIntent : Intent() {
    class UpdateCurrentPassword(val value: String) : EditPasswordIntent()
    class UpdateNewPassword(val value: String) : EditPasswordIntent()
    class UpdateNewPasswordRepeat(val value: String) : EditPasswordIntent()

    data object ConfirmPassword : EditPasswordIntent()
    data object SetPassword : EditPasswordIntent()
}

sealed class EditPasswordSideEffect : SideEffect() {
    data object ConfirmPasswordPasswordConfirmed : EditPasswordSideEffect()
    data object ConfirmPasswordPasswordMismatch : EditPasswordSideEffect()
    data object SetPasswordPasswordEdited : EditPasswordSideEffect()
    data object SetPasswordPasswordIncorrect : EditPasswordSideEffect()
}
