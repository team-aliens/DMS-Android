package team.aliens.domain._param.schools.queryfeatures

/**
 * @author junsuPark
 * A response returned when querying user features
 * @property mealService a boolean value of whether meal service enabled
 * @property noticeService a boolean value of whether notice service enabled
 * @property pointService a boolean value of whether point service enabled
 * @property studyRoomService a boolean value of whether study room service enabled
 * @property remainService a boolean value of whether remain service enabled
 */
data class QueryFeaturesResponse(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainService: Boolean,
)
