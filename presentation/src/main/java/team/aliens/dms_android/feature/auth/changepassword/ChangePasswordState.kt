package team.aliens.dms_android.feature.auth.changepassword

import team.aliens.dms_android.base.MviState

data class ChangePasswordState(
    val accountId: String,
    val name: String,
    val email: String,
    val authCode: String,
    val currentPassword: String,
    val repeatPassword: String,
    val newPassword: String,
    val id: String,
) : MviState {

    companion object {
        fun getDefaultInstance() =
            ChangePasswordState(
                accountId = "",
                name = "",
                email = "",
                authCode = "",
                currentPassword = "",
                repeatPassword = "",
                newPassword = "",
                id = "",
            )
    }
}
