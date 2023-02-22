package team.aliens.domain.entity.remain

import java.util.*

data class RemainOptionsEntity(
    val selectedOption: String?,
    val remainOptionEntities: List<RemainOptionEntity>,
) {
    data class RemainOptionEntity(
        val id: UUID,
        val title: String,
        val description: String,
    )
}
