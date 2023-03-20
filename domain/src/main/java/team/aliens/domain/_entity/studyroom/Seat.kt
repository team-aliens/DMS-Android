package team.aliens.domain._entity.studyroom

import java.util.*

/**
 * @author junsuPark
 * A set of seat information
 * @property id unique id of the seat
 * @property name name of the seat
 * @property color color of the seat
 */
data class Seat(
    val id: UUID,
    val name: String,
    val color: String,
)
