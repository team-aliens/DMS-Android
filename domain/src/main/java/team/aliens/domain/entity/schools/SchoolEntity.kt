package team.aliens.domain.entity.schools

import java.util.*

data class SchoolEntity(
    val id: UUID? = null,
    val name: String,
    val address: String,
)
