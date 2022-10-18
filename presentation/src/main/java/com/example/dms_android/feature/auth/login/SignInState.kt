package com.example.dms_android.feature.auth.login

import com.example.dms_android.base.MviState

data class SignInState(
    val id: String,
    val password: String
) : MviState {
    companion object {
        fun initial() =
            SignInState(id = "", password = "")
    }
}
