package team.aliens.domain.model.remains

import java.util.*

/**
 * A response returned when fetching remains options
 * @property remainOptions list of remain options
 */
data class FetchRemainsOptionsOutput(
    val remainOptions: List<RemainsOptionInformation>,
) {

    /**
     * A set of information about remains option
     * @property id the unique id of the remains option
     * @property title the title of the remains option
     * @property description description of the remains option
     * @property isApplied a boolean value whether current user applied the remains option
     */
    data class RemainsOptionInformation(
        val id: UUID,
        val title: String,
        val description: String,
        val isApplied: Boolean,
    )
}
