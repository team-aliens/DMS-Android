package team.aliens.dms.android.feature.editpassword

import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState

internal class EditPasswordViewModel :
    BaseMviViewModel<EditPasswordUiState, EditPasswordIntent, EditPasswordSideEffect>(
        initialState = EditPasswordUiState.initial(),
    ) {

}

internal data class EditPasswordUiState(
    val oldPassword: String,
    val newPassword: String,
    val newPasswordConfirmation: String,
) : UiState() {
    companion object {
        fun initial() = EditPasswordUiState(
            oldPassword = "",
            newPassword = "",
            newPasswordConfirmation = "",
        )
    }
}

internal sealed class EditPasswordIntent : Intent()

internal sealed class EditPasswordSideEffect : SideEffect()
