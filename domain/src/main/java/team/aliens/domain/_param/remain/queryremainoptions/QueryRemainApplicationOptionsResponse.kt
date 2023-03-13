package team.aliens.domain._param.remain.queryremainoptions

import java.util.*

/**
 * @author junsuPark
 * A response returned when querying remain application options
 * @property remainOptions list of remain options
 */
data class QueryRemainApplicationOptionsResponse(
    val remainOptions: List<RemainOptionInformation>,
) {

    /**
     * @author junsuPark
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
