package team.aliens.domain.model.remains

import java.time.DayOfWeek

/**
 * A response returned when fetching remains application time
 * @property startDayOfWeek the day when remain application starts up
 * @property startTime the start time of the day when remains application starts
 * @property endDayOfWeek the day when remains application ends up
 * @property endTime the end time of the day when remains application ends
 */
data class FetchRemainsApplicationTimeOutput(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
