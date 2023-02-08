package team.aliens.domain.param

import java.util.*

data class ExamineGradeParam(
    val schoolId: UUID,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
)