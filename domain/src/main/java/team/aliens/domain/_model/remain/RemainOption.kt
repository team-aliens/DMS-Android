package team.aliens.domain._model.remain

import java.util.*

/**
 * An information set of remain option
 * @property id the unique id of remain option
 * @property title title of the remain option
 * @property description description of the remain option
 * @property applied boolean value of whether current user applied the study room
 */
data class RemainOption(
    val id: UUID,
    val title: String,
    val description: String,
    val applied: Boolean,
)
