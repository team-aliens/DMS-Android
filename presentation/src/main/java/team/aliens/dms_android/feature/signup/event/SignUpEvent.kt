package team.aliens.dms_android.feature.signup.event

import team.aliens.dms_android.base.MviEvent

sealed class SignUpEvent : MviEvent {
    object SignUpSuccess : SignUpEvent()
    object BadRequestException : SignUpEvent()
    object UnAuthorizedException : SignUpEvent()
    object NotFoundException : SignUpEvent()
    object ConflictException : SignUpEvent()
    object TooManyRequestsException : SignUpEvent()
    object InternalServerException : SignUpEvent()
    object UnKnownException : SignUpEvent()
}