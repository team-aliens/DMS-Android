package team.aliens.domain._model.remain

import java.time.DayOfWeek

/**
 * A set of room time information
 * @property startDayOfWeek the start day of remain application
 * @property startTime the start time of remain application
 * @property endDayOfWeek the end day of remain application
 * @property endTime the end time of remain application
 */
data class RemainApplicationTime(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
