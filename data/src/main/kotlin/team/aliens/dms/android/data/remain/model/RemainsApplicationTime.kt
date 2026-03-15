package team.aliens.dms.android.data.remain.model

import java.time.DayOfWeek

data class RemainsApplicationTime(
    val startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    val startTime: String  = "",
    val endDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    val endTime: String = "",
)
