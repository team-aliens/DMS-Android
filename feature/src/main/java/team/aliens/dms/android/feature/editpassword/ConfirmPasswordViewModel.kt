package team.aliens.dms.android.feature.editpassword

import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState

internal class ConfirmPasswordViewModel :
    BaseMviViewModel<EditPasswordUiState, EditPasswordIntent, EditPasswordSideEffect>(
        initialState = EditPasswordUiState.initial(),
    ) {
    override fun processIntent(intent: EditPasswordIntent) {
        when (intent) {
            EditPasswordIntent.Next -> {
                TODO()
            }

            is EditPasswordIntent.UpdatePassword -> updateOldPassword(password = intent.password)
            is EditPasswordIntent.UpdatePasswordShowing -> updatePasswordShowing(show = intent.show)
        }
    }

    private fun updateOldPassword(password: String) =
        reduce(stateFlow.value.copy(password = password))

    private fun updatePasswordShowing(show: Boolean) =
        reduce(stateFlow.value.copy(showPassword = show))
}

internal data class EditPasswordUiState(
    val password: String,
    val showPassword: Boolean,
) : UiState() {
    companion object {
        fun initial() = EditPasswordUiState(
            password = "",
            showPassword = false,
        )
    }
}

internal sealed class EditPasswordIntent : Intent() {
    class UpdatePassword(val password: String) : EditPasswordIntent()
    class UpdatePasswordShowing(val show: Boolean) : EditPasswordIntent()
    data object Next : EditPasswordIntent()
}

internal sealed class EditPasswordSideEffect : SideEffect() {
    data object Loading : EditPasswordSideEffect()
    data object PasswordIncorrect : EditPasswordSideEffect()
    data object ConfirmationSuccess : EditPasswordSideEffect()
}
