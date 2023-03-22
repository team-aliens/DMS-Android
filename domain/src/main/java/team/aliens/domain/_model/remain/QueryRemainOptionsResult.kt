package team.aliens.domain._model.remain

import java.util.*

/**
 * A response returned when querying remain options
 * @property remainOptions list of remain options
 */
data class QueryRemainOptionsResult(
    val remainOptions: List<RemainOptionInformation>,
) {

    /**
     * A set of information about remain option
     * @property id the unique id of the remain option
     * @property title the title of the remain option
     * @property description description of the remain option
     * @property isApplied a boolean value whether current user applied the remain option
     */
    data class RemainOptionInformation(
        val id: UUID,
        val title: String,
        val description: String,
        val isApplied: Boolean,
    )
}
