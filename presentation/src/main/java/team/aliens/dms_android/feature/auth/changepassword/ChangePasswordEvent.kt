package team.aliens.dms_android.feature.auth.changepassword

import com.example.dms_android.base.MviEvent

sealed class ChangePasswordEvent: MviEvent {
    object ChangePasswordSuccess: ChangePasswordEvent()
    object BadRequestException: ChangePasswordEvent()
    object UnAuthorizedException: ChangePasswordEvent()
    object NotFoundException: ChangePasswordEvent()
    object TooManyRequestException: ChangePasswordEvent()
    object InternalServerException: ChangePasswordEvent()
    object UnKnownException: ChangePasswordEvent()
}
