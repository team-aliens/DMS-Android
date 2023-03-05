package team.aliens.dms_android.feature.auth.changepassword

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

    data class SetId(val id: String): ChangePasswordEvent()
    data class SetName(val name: String): ChangePasswordEvent()
    data class SetEmail(val email: String): ChangePasswordEvent()
    data class SetAuthCode(val authCode: String): ChangePasswordEvent()
}
