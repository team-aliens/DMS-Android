package team.aliens.domain._param.schools.queryfeatures

/**
 * @author junsuPark
 * A response returned when querying user features
 * [mealService] a boolean value of whether meal service enabled
 * [noticeService] a boolean value of whether notice service enabled
 * [pointService] a boolean value of whether point service enabled
 * [studyRoomService] a boolean value of whether study room service enabled
 * [remainService] a boolean value of whether remain service enabled
 */
data class QueryFeaturesResponse(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainService: Boolean,
)
