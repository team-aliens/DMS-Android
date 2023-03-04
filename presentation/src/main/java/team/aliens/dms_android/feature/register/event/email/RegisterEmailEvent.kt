package team.aliens.dms_android.feature.register.event.email

import team.aliens.dms_android.base.MviEvent

sealed class RegisterEmailEvent : MviEvent {
    object SendEmailSuccess : RegisterEmailEvent()
    object CheckEmailSuccess : RegisterEmailEvent()
    object BadRequestException : RegisterEmailEvent()
    object CheckEmailNotFound : RegisterEmailEvent()
    object CheckEmailUnauthorized : RegisterEmailEvent()
    object TooManyRequestsException : RegisterEmailEvent()
    object InternalServerException : RegisterEmailEvent()
    object UnKnownException : RegisterEmailEvent()

    object AllowEmail: RegisterEmailEvent()
    object ConflictException: RegisterEmailEvent()
}
