package team.aliens.dms.android.domain.model.point

import team.aliens.dms.android.domain.model._common.PointType
import java.util.UUID

/**
 * An entity of point, contains date point given, point type, point name, and its score
 * @property id the point's unique id
 * @property date date when was the point given
 * @property type type of the point, bonus or minus
 * @property name name of the point
 * @property score score of the point
 */
data class Point(
    val id: UUID,
    val date: String,
    val type: PointType,
    val name: String,
    val score: Int,
)
