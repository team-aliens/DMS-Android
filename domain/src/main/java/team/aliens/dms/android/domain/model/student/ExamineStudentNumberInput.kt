package team.aliens.dms.android.domain.model.student

import java.util.UUID

data class ExamineStudentNumberInput(
    val schoolId: UUID,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
)
