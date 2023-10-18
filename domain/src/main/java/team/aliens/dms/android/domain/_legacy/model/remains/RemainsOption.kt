package team.aliens.dms.android.domain._legacy.model.remains

import java.util.*

/**
 * An information set of remains option
 * @property id the unique id of remains option
 * @property title title of the remains option
 * @property description description of the remains option
 * @property applied boolean value of whether current user applied the study room
 */
data class RemainsOption(
    val id: UUID,
    val title: String,
    val description: String,
    val applied: Boolean,
)
