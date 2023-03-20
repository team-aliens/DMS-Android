package team.aliens.domain._entity.studyroom

import java.util.*

/**
 * @author junsuPark
 * A set of room time information
 * @property id unique id of study room
 * @property startTime the start time of room application
 * @property endTime the end time of room application
 */
data class RoomApplicationTime(
    val id: UUID,
    val startTime: String,
    val endTime: String,
)
