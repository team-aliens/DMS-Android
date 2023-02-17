package team.aliens.domain.entity.remain

import java.util.*

data class RemainOptionsEntity(
    val selectedOption: String,
    val remainOptions: List<RemainOption>,
) {
    data class RemainOption(
        val id: UUID,
        val title: String,
        val description: String,
    )
}
