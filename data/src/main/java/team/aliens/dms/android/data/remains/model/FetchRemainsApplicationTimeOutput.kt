package team.aliens.dms.android.data.remains.model

import org.threeten.bp.DayOfWeek

data class FetchRemainsApplicationTimeOutput(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
