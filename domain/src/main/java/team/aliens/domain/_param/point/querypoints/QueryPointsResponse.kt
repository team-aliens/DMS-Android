package team.aliens.domain._param.point.querypoints

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
    ) {

        /**
         * @author junsuPark
         * An enum class of point type
         * @property BONUS a value of bonus point
         * @property MINUS a value of minus point
         */
        enum class PointType {
            BONUS, MINUS,
            ;
        }
    }
}
