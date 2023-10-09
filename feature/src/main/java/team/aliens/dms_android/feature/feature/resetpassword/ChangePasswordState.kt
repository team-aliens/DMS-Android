package team.aliens.dms_android.feature.feature.resetpassword

import team.aliens.dms_android.feature.base._MviState

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
