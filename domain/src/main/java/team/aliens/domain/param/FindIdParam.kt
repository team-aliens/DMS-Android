package team.aliens.domain.param

import java.util.*

data class FindIdParam(
    val schoolId: UUID,
    val name: String,
    val grade: Int,
    val classRoom: Int,
    val number: Int
)