package com.example.dms_android.feature.auth.login

import com.example.dms_android.base.MviEvent

sealed class SignInEvent : MviEvent {
    data class InputId(val id: String) : SignInEvent()
    data class InputPassword(val password: String) : SignInEvent()
}
