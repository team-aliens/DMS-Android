package com.example.dms_android.feature.register.event

import com.example.dms_android.base.MviEvent

sealed class SignUpEvent : MviEvent {
    data class InputSchoolCode(val schoolCode: String) : SignUpEvent()
    data class InputSchoolAnswer(val schoolAnswer: String) : SignUpEvent()
    data class InputEmail(val email: String) : SignUpEvent()
    data class InputAuthCode(val authCode: String) : SignUpEvent()
    data class InputGrade(val grade: Int) : SignUpEvent()
    data class InputClassRoom(val classRoom: Int) : SignUpEvent()
    data class InputNumber(val number: Int) : SignUpEvent()
    data class InputProfileImageUrl(val profileImageUrl: String) : SignUpEvent()
    data class InputAccountId(val accountId: String) : SignUpEvent()
    data class InputPassword(val password: String) : SignUpEvent()

    object SignUpSuccess : SignUpEvent()
    object BadRequestException : SignUpEvent()
    object UnAuthorizedException : SignUpEvent()
    object NotFoundException : SignUpEvent()
    object ConflictException : SignUpEvent()
    object TooManyRequestsException : SignUpEvent()
    object InternalServerException : SignUpEvent()
    object UnKnownException : SignUpEvent()
}