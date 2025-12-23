package team.aliens.dms.android.data.remains.model

import java.time.DayOfWeek

data class RemainsApplicationTime(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
