package team.aliens.domain._model.studyroom

import java.util.*

/**
 * A response returned when querying seat types
 * @property types list of seat types
 */
data class QuerySeatTypesResult(
    val types: List<SeatTypeInformation>,
) {

    /**
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
