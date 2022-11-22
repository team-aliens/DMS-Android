package com.example.dms_android.feature.register.event.email

import com.example.dms_android.base.MviEvent
import com.example.dms_android.feature.register.event.SignUpEvent

sealed class RegisterEmailEvent : MviEvent {
    object SendEmailSuccess : RegisterEmailEvent()
    object CheckEmailSuccess : RegisterEmailEvent()
    object BadRequestException : RegisterEmailEvent()
    object CheckEmailNotFound : RegisterEmailEvent()
    object CheckEmailUnauthorized : RegisterEmailEvent()
    object TooManyRequestsException : RegisterEmailEvent()
    object InternalServerException : RegisterEmailEvent()
    object UnKnownException : RegisterEmailEvent()
}
