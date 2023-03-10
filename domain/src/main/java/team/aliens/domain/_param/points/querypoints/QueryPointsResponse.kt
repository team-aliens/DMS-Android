package team.aliens.domain._param.points.querypoints

/**
 * @author junsuPark
 * A response returned when querying points
 * [totalPoint] total points, summary of bonus and minus points
 * [points] list of points
 */
data class QueryPointsResponse(
    val totalPoint: Int,
    val points: List<PointInformation>,
) {

    /**
     * @author junsuPark
     * A set of point information
     * [date] date of the specific point
     * [type] the type of point
     */
    data class PointInformation(
        val date: String,
        val type: PointType,
    ) {

        /**
         * @author junsuPark
         * An enum class of point type
         * [BONUS] a value of bonus point
         * [MINUS] a value of minus point
         */
        enum class PointType {
            BONUS, MINUS,
            ;
        }
    }
}
