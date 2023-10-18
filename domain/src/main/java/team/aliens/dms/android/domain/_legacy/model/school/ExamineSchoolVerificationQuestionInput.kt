package team.aliens.dms.android.domain._legacy.model.school

import java.util.UUID

data class ExamineSchoolVerificationQuestionInput(
    val schoolId: UUID,
    val answer: String,
)
