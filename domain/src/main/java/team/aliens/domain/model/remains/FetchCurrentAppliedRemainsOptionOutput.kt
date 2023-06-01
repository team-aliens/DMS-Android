package team.aliens.domain.model.remains

import java.util.UUID

/**
 * A response returned when fetching current applied remains option
 * @property appliedRemainsOptionId the unique id of the remains option
 * @property title the title of the remains option
 */
data class FetchCurrentAppliedRemainsOptionOutput(
    val appliedRemainsOptionId: UUID,
    val title: String,
)
