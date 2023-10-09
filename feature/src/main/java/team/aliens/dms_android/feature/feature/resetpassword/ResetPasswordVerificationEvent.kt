package team.aliens.dms_android.feature.feature.resetpassword

import team.aliens.dms_android.feature.base.MviEvent

sealed class ResetPasswordVerificationEvent : MviEvent {
    object SendEmailSuccess : ResetPasswordVerificationEvent()
    object CheckEmailSuccess : ResetPasswordVerificationEvent()
    object BadRequestException : ResetPasswordVerificationEvent()
    object CheckEmailNotFound : ResetPasswordVerificationEvent()
    object CheckEmailUnauthorized : ResetPasswordVerificationEvent()
    object TooManyRequestsException : ResetPasswordVerificationEvent()
    object InternalServerException : ResetPasswordVerificationEvent()
    object UnKnownException : ResetPasswordVerificationEvent()

    object AllowEmail: ResetPasswordVerificationEvent()
    object ConflictException: ResetPasswordVerificationEvent()
}
