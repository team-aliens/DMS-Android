package team.aliens.domain.entity.remain

import java.time.DayOfWeek

data class AvailableRemainTimeEntity(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
