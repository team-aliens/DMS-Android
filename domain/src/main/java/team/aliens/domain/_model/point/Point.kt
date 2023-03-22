package team.aliens.domain._model.point

import team.aliens.domain._model._common.PointType

/**
 * An entity of point, contains date point given, point type, point name, and its score
 * @property date date when was the point given
 * @property type type of the point, bonus or minus
 * @property name name of the point
 * @property score score of the point
 */
data class Point(
    val date: String,
    val type: PointType,
    val name: String,
    val score: Int,
)
