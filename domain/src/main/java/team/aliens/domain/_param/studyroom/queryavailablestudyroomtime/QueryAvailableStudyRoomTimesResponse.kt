package team.aliens.domain._param.studyroom.queryavailablestudyroomtime

import java.util.*

/**
 * @author junsuPark
 * A response returned when querying available study room time
 * @property timeSlots list of available study room times
 */
data class QueryAvailableStudyRoomTimesResponse(
    val timeSlots: List<TimeSlotInformation>,
) {

    /**
     * @author junsuPark
     * A set of available time slot information
     * @property id the time slot's unique id
     * @property name the name of the time slot
     */
    data class TimeSlotInformation(
        val id: UUID,
        val name: String,
    )
}
