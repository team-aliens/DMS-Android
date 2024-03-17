package team.aliens.dms.android.data.outing.model

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import java.util.UUID

data class OutingApplicationTime(
    val id: UUID,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val enabled: Boolean,
    val dayOfWeek: DayOfWeek,
)
