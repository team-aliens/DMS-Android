package team.aliens.dms_android._feature.auth.resetpassword

import team.aliens.dms_android.base.MviEvent

sealed class ChangePasswordEvent : MviEvent {
    object ChangePasswordSuccess : ChangePasswordEvent()
    object BadRequestException : ChangePasswordEvent()
    object UnAuthorizedException : ChangePasswordEvent()
    object NotFoundException : ChangePasswordEvent()
    object TooManyRequestException : ChangePasswordEvent()
    object InternalServerException : ChangePasswordEvent()
    object UnKnownException : ChangePasswordEvent()

    data class SetCurrentPassword(val currentPassword: String): ChangePasswordEvent()
    data class SetRepeatPassword(val repeatPassword: String): ChangePasswordEvent()
    data class SetNewPassword(val newPassword: String): ChangePasswordEvent()
}
