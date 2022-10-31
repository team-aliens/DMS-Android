package com.example.dms_android.feature.register.event.school

import com.example.dms_android.base.MviEvent

sealed class CompareSchoolAnswerEvent : MviEvent {
    data class InputSchoolAnswer(val schoolAnswer: String) : CompareSchoolAnswerEvent()
    object CompareSchoolAnswerSuccess : CompareSchoolAnswerEvent()
    object BadRequestException : CompareSchoolAnswerEvent()
    object UnAuthorizedException : CompareSchoolAnswerEvent()
    object NotFoundException : CompareSchoolAnswerEvent()
    object TooManyRequestException : CompareSchoolAnswerEvent()
    object InternalServerException : CompareSchoolAnswerEvent()
    object UnKnownException : CompareSchoolAnswerEvent()
}
