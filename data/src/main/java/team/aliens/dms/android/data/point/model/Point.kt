package team.aliens.dms.android.data.point.model

import org.threeten.bp.LocalDate
import java.util.UUID

data class Point(
    val id: UUID,
    val date: LocalDate,
    val type: PointType,
    val name: String,
    val score: Int,
)
