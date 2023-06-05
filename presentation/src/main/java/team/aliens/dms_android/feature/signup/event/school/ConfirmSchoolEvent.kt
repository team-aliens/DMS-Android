package team.aliens.dms_android.feature.signup.event.school

import team.aliens.domain.model.school.FetchSchoolVerificationQuestionOutput

sealed interface ConfirmSchoolEvent
data class FetchSchoolQuestion(
    val fetchSchoolVerificationQuestionOutput: FetchSchoolVerificationQuestionOutput,
) : ConfirmSchoolEvent

object CompareSchoolAnswerSuccess : ConfirmSchoolEvent

object NotFoundCompareSchool : ConfirmSchoolEvent

object MissMatchCompareSchool : ConfirmSchoolEvent

