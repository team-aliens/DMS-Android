package team.aliens.dms_android.domain.model.studyroom

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
     * @property startTime the available start time of slot
     * @property endTime the available end time of slot
     */
    data class TimeSlotInformation(
        val id: UUID,
        val startTime: String,
        val endTime: String,
    )
}
