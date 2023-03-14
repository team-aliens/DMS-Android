package team.aliens.domain._param.point

import team.aliens.domain._common.PointType

/**
 * @author junsuPark
 * A response returned when querying points
 * @property totalPoint total points, summary of bonus and minus points
 * @property points list of points
 */
data class QueryPointsResponse(
    val totalPoint: Int,
    val points: List<PointInformation>,
) {

    /**
     * @author junsuPark
     * A set of point information
     * @property date date of the specific point
     * @property type the type of point
     */
    data class PointInformation(
        val date: String,
        val type: PointType,
    )
}
