package team.aliens.dms_android.feature._feature.resetpassword

import team.aliens.dms_android.feature._legacy.base.MviEvent

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
