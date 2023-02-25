package team.aliens.dms_android.feature.register.event.school

import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity

sealed interface ConfirmSchoolEvent
    data class FetchSchoolQuestion(val schoolConfirmQuestionEntity: SchoolConfirmQuestionEntity) :
        ConfirmSchoolEvent

    object CompareSchoolAnswerSuccess : ConfirmSchoolEvent

    object NotFoundCompareSchool : ConfirmSchoolEvent

    object MissMatchCompareSchool : ConfirmSchoolEvent

