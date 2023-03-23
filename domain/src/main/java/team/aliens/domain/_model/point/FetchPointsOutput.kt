package team.aliens.domain._model.point

import team.aliens.domain._model._common.PointType

/**
 * A response returned when fetching points
 * @property totalPoint total points, summary of bonus and minus points
 * @property points list of points
 */
data class FetchPointsOutput(
    val totalPoint: Int,
    val points: List<PointInformation>,
) {

    /**
     * A set of point information
     * @property date date of the specific point
     * @property type the type of point
     * @property name the name of point
     * @property score the score of point
     */
    data class PointInformation(
        val date: String,
        val type: PointType,
        val name: String,
        val score: Int,
    )
}
