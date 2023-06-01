package team.aliens.domain.model.remains

import java.util.UUID

/**
 * An information set current applied remains option
 * @property appliedRemainsOptionId the unique id of the remains option
 * @property title the title of the remains option
 */
data class CurrentAppliedRemainsOption(
    val appliedRemainsOptionId: UUID,
    val title: String,
)