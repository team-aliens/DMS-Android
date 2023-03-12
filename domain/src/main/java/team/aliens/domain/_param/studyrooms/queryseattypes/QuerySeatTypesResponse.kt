package team.aliens.domain._param.studyrooms.queryseattypes

import java.util.*

/**
 * @author junsuPark
 * A response returned when querying seat types
 * @property types list of seat types
 */
data class QuerySeatTypesResponse(
    val types: List<SeatTypeInformation>,
) {

    /**
     * @author junsuPark
     * A set of seat information
     * @property id an unique id of the seat
     * @property name the name of the seat type
     * @property color color of the seat type
     */
    data class SeatTypeInformation(
        val id: UUID,
        val name: String,
        val color: String,
    )
}
