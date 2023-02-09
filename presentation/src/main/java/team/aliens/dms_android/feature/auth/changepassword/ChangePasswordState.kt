package team.aliens.dms_android.feature.auth.changepassword

import team.aliens.dms_android.base.MviState

data class ChangePasswordState(
    val accountId: String,
    val name: String,
    val email: String,
    val authCode: String,
    val newPassword: String,
) : MviState {

    companion object {
        fun initial() =
            ChangePasswordState(
                accountId = "",
                name = "",
                email = "",
                authCode = "",
                newPassword = "",
            )
    }
}
