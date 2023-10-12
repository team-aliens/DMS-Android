package team.aliens.dms_android.feature._feature.resetpassword

import team.aliens.dms_android.feature._legacy.base.MviEvent

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
