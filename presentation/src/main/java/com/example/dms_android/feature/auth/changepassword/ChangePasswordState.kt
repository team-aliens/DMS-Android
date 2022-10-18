package com.example.dms_android.feature.auth.changepassword

import com.example.dms_android.base.MviState

data class ChangePasswordState(
    val accountId: String,
    val name: String,
    val email: String,
    val auth_code: String,
    val new_password: String,
) : MviState {

    companion object {
        fun initial() =
            ChangePasswordState(
                accountId = "",
                name = "",
                email = "",
                auth_code = "",
                new_password = "",
            )
    }
}
