package team.aliens.domain.model.remains

import java.time.DayOfWeek

/**
 * A set of room time information
 * @property startDayOfWeek the start day of remains application
 * @property startTime the start time of remains application
 * @property endDayOfWeek the end day of remains application
 * @property endTime the end time of remains application
 */
data class RemainsApplicationTime(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
