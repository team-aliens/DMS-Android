package team.aliens.dms_android.feature.register.event.school

import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput

sealed interface ConfirmSchoolEvent
data class FetchSchoolQuestion(
    val fetchSchoolVerificationQuestionOutput: FetchSchoolVerificationQuestionOutput,
) : ConfirmSchoolEvent

object CompareSchoolAnswerSuccess : ConfirmSchoolEvent

object NotFoundCompareSchool : ConfirmSchoolEvent

object MissMatchCompareSchool : ConfirmSchoolEvent

