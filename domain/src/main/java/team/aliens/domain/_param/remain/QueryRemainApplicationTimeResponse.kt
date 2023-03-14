package team.aliens.domain._param.remain

import java.time.DayOfWeek

/**
 * @author junsuPark
 * A response returned when querying remain application time
 * @property startDayOfWeek the day when remain application starts up
 * @property startTime the start time of the day when remain application starts
 * @property endDayOfWeek the day when remain application ends up
 * @property endTime the end time of the day when remain application ends
 */
data class QueryRemainApplicationTimeResponse(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
