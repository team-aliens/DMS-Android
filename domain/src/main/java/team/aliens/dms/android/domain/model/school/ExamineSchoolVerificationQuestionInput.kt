package team.aliens.dms.android.domain.model.school

import java.util.UUID

data class ExamineSchoolVerificationQuestionInput(
    val schoolId: UUID,
    val answer: String,
)
