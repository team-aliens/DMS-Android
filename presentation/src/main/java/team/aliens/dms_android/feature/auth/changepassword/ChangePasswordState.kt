package team.aliens.dms_android.feature.auth.changepassword

import team.aliens.dms_android.base.MviState

data class ChangePasswordState(
    val currentPassword: String,
    val repeatPassword: String,
    val newPassword: String,
) : MviState {

    companion object {
        fun getDefaultInstance() =
            ChangePasswordState(
                currentPassword = "",
                repeatPassword = "",
                newPassword = "",
            )
    }
}
