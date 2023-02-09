package team.aliens.dms_android.feature.register.event.school

import team.aliens.dms_android.base.MviEvent
import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity

sealed class ConfirmSchoolEvent : MviEvent {
    data class FetchSchoolQuestion(val schoolConfirmQuestionEntity: SchoolConfirmQuestionEntity) :
        ConfirmSchoolEvent()

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

