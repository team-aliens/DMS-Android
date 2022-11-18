package com.example.dms_android.feature.register.event.email

import com.example.dms_android.base.MviEvent

sealed class RegisterEmailEvent : MviEvent {
    object SendEmailSuccess : RegisterEmailEvent()
    object CheckEmailSuccess : RegisterEmailEvent()
    object CheckEmailUnauthorized : RegisterEmailEvent()
    data class ErrorMessage(val message: String) : RegisterEmailEvent()
}
