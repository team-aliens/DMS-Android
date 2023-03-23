package team.aliens.domain._model.studyroom

import java.util.*

/**
 * A response returned when fetching available study room time
 * @property timeSlots list of available study room times
 */
data class FetchAvailableStudyRoomTimesOutput(
    val timeSlots: List<TimeSlotInformation>,
) {

    /**
     * A set of available time slot information
     * @property id the time slot's unique id
     * @property name the name of the time slot
     */
    data class TimeSlotInformation(
        val id: UUID,
        val name: String,
    )
}
