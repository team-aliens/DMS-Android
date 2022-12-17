package com.example.dms_android.feature.register.event.school

import com.example.auth_domain.entity.SchoolConfirmQuestionEntity
import com.example.dms_android.base.MviEvent

sealed class ConfirmSchoolEvent : MviEvent {
    data class FetchSchoolQuestion(val schoolConfirmQuestionEntity: SchoolConfirmQuestionEntity) : ConfirmSchoolEvent()
    object SchoolQuestionSuccess : ConfirmSchoolEvent()
    object CompareSchoolAnswerSuccess : ConfirmSchoolEvent()
    object CompareSchoolBadRequestException : ConfirmSchoolEvent()
    object CompareSchoolUnauthorizedException : ConfirmSchoolEvent()
    object CompareSchoolNotFoundException : ConfirmSchoolEvent()
    object CompareSchoolTooManyRequestException : ConfirmSchoolEvent()
    object CompareSchoolInternalServerException : ConfirmSchoolEvent()
    object SchoolQuestionBadRequestException : ConfirmSchoolEvent()
    object SchoolQuestionNotFoundException : ConfirmSchoolEvent()
    object SchoolQuestionTooManyRequestException : ConfirmSchoolEvent()
    object SchoolQuestionInternalServerException : ConfirmSchoolEvent()
    object UnknownException : ConfirmSchoolEvent()
}

