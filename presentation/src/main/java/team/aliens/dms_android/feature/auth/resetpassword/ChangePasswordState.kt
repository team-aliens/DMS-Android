package team.aliens.dms_android.feature.auth.resetpassword

import team.aliens.dms_android.base._MviState

data class ChangePasswordState(
    val currentPassword: String,
    val repeatPassword: String,
    val newPassword: String,
) : _MviState {

    companion object {
        fun getDefaultInstance() =
            ChangePasswordState(
                currentPassword = "",
                repeatPassword = "",
                newPassword = "",
            )
    }
}
