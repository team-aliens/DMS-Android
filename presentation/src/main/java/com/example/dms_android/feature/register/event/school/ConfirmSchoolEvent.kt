package com.example.dms_android.feature.register.event.school

import com.example.dms_android.base.MviEvent

sealed class ConfirmSchoolEvent : MviEvent {
    object SchoolQuestionSuccess : ConfirmSchoolEvent()
    object CompareSchoolAnswerSuccess : ConfirmSchoolEvent()
    object CompareSchoolBadRequest : ConfirmSchoolEvent()
    object CompareSchoolUnauthorized : ConfirmSchoolEvent()
    object CompareSchoolNotFound : ConfirmSchoolEvent()
    object SchoolQuestionBadRequest : ConfirmSchoolEvent()
    object SchoolQuestionNotFound : ConfirmSchoolEvent()
    object TooManyRequestException : ConfirmSchoolEvent()
    object InternalServerException : ConfirmSchoolEvent()
    object UnknownException : ConfirmSchoolEvent()
}

