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
internal class EditPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<EditPasswordUiState, EditPasswordIntent, EditPasswordSideEffect>(
    initialState = EditPasswordUiState.initial(),
) {
    override fun processIntent(intent: EditPasswordIntent) {
        when (intent) {
            is EditPasswordIntent.UpdateCurrentPassword -> this.updateCurrentPassword(value = intent.value)
            EditPasswordIntent.ConfirmPassword -> this.confirmPassword()
        }
    }

    private fun updateCurrentPassword(value: String) = reduce(
        newState = stateFlow.value.copy(
            currentPassword = value,
            confirmButtonAvailable = this.checkConfirmButtonAvailable(),
        ),
    )

    private fun confirmPassword() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            userRepository.comparePassword(password = stateFlow.value.currentPassword)
        }.onSuccess {
            postSideEffect(EditPasswordSideEffect.PasswordConfirmed)
        }.onFailure {
            when (it) {
// TODO
            }
            postSideEffect(EditPasswordSideEffect.PasswordMismatch)
        }
    }

    private fun checkConfirmButtonAvailable() = stateFlow.value.currentPassword.isNotBlank()
}

internal data class EditPasswordUiState(
    val currentPassword: String,
    val confirmButtonAvailable: Boolean,
) : UiState() {
    companion object {
        fun initial() = EditPasswordUiState(
            currentPassword = "",
            confirmButtonAvailable = false,
        )
    }
}

internal sealed class EditPasswordIntent : Intent() {
    class UpdateCurrentPassword(val value: String) : EditPasswordIntent()

    data object ConfirmPassword : EditPasswordIntent()
}

internal sealed class EditPasswordSideEffect : SideEffect() {
    data object PasswordConfirmed : EditPasswordSideEffect()
    data object PasswordMismatch : EditPasswordSideEffect()
}

/*

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.user.repository.UserRepository
import javax.inject.Inject

internal class EditPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<EditPasswordUiState, EditPasswordIntent, EditPasswordSideEffect>(
    initialState = EditPasswordUiState.initial(),
) {
    override fun processIntent(intent: EditPasswordIntent) {
        when (intent) {
            EditPasswordIntent.ComparePassword -> comparePassword()

            is EditPasswordIntent.UpdateCurrentPassword -> updateCurrentPassword(intent.password)
            is EditPasswordIntent.UpdateCurrentPasswordShowing ->
                updateCurrentPasswordShowing(intent.shouldShow)

            is EditPasswordIntent.UpdateNewPassword -> updateNewPassword(intent.password)
            is EditPasswordIntent.UpdateNewPasswordShowing -> updateNewPasswordShowing(intent.shouldShow)

            is EditPasswordIntent.UpdateNewPasswordConfirmation ->
                updateNewPasswordConfirmation(intent.password)

            is EditPasswordIntent.UpdateNewPasswordConfirmationShowing ->
                updateNewPasswordConfirmationShowing(intent.shouldShow)
        }
    }

    private fun comparePassword() {
        val password = stateFlow.value.currentPassword
        if (password.isBlank()) {
            postSideEffect(EditPasswordSideEffect.CurrentPasswordEmpty)
            return
        }

        postSideEffect(EditPasswordSideEffect.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val result = runCatching {
                userRepository.comparePassword(password)
            }
            postSideEffect(
                if (result.isSuccess) {
                    EditPasswordSideEffect.ConfirmationSuccess
                } else {
                    EditPasswordSideEffect.CurrentPasswordIncorrect
                }
            )
        }
    }

    private fun updateCurrentPassword(password: String) =
        reduce(stateFlow.value.copy(currentPassword = password))

    private fun updateCurrentPasswordShowing(shouldShow: Boolean) =
        reduce(stateFlow.value.copy(currentPasswordShowing = shouldShow))

    private fun updateNewPassword(password: String) =
        reduce(stateFlow.value.copy(newPassword = password))

    private fun updateNewPasswordShowing(shouldShow: Boolean) =
        reduce(stateFlow.value.copy(newPasswordShowing = shouldShow))

    private fun updateNewPasswordConfirmation(password: String) =
        reduce(stateFlow.value.copy(newPasswordConfirmation = password))

    private fun updateNewPasswordConfirmationShowing(shouldShow: Boolean) =
        reduce(stateFlow.value.copy(newPasswordConfirmationShowing = shouldShow))
}

internal data class EditPasswordUiState(
    val currentPassword: String,
    val currentPasswordShowing: Boolean,
    val newPassword: String,
    val newPasswordShowing: Boolean,
    val newPasswordConfirmation: String,
    val newPasswordConfirmationShowing: Boolean,
) : UiState() {
    companion object {
        fun initial() = EditPasswordUiState(
            currentPassword = "",
            currentPasswordShowing = false,
            newPassword = "",
            newPasswordShowing = false,
            newPasswordConfirmation = "",
            newPasswordConfirmationShowing = false,
        )
    }
}

internal sealed class EditPasswordIntent : Intent() {
    class UpdateCurrentPassword(val password: String) : EditPasswordIntent()
    class UpdateCurrentPasswordShowing(val shouldShow: Boolean) : EditPasswordIntent()
    class UpdateNewPassword(val password: String) : EditPasswordIntent()
    class UpdateNewPasswordShowing(val shouldShow: Boolean) : EditPasswordIntent()
    class UpdateNewPasswordConfirmation(val password: String) : EditPasswordIntent()
    class UpdateNewPasswordConfirmationShowing(val shouldShow: Boolean) : EditPasswordIntent()
    data object ComparePassword : EditPasswordIntent()
}

internal sealed class EditPasswordSideEffect : SideEffect() {
    data object Loading : EditPasswordSideEffect()
    data object CurrentPasswordEmpty : EditPasswordSideEffect()
    data object CurrentPasswordIncorrect : EditPasswordSideEffect()
    data object ConfirmationSuccess : EditPasswordSideEffect()
}
*/
