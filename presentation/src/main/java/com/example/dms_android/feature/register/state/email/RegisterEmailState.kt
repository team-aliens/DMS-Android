package com.example.dms_android.feature.register.state.email

import com.example.dms_android.base.MviState

data class RegisterEmailState(
    val email: String,
) : MviState {
    companion object {
        fun initial() = RegisterEmailState(
            email = ""
        )
    }
}