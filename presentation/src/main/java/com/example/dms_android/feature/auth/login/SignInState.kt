package com.example.dms_android.feature.auth.login

import com.example.dms_android.base.MviState

data class SignInState(
    val id: String,
    val password: String,
    var autoLogin: Boolean,
) : MviState {
    companion object {
        fun initial() =
            SignInState(id = "", password = "", autoLogin = false)
    }
}
