package team.aliens.domain.entity.remain

import java.time.DayOfWeek

data class AvailableRemainTimeEntity(
    val startDayOfWeek: DayOfWeek,
    val startsAt: String,
    val endDayOfWeek: DayOfWeek,
    val endsAt: String,
)
