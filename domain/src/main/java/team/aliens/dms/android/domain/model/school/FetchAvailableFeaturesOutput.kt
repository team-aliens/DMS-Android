package team.aliens.dms.android.domain.model.school

/**
 * A response returned when fetching available user features
 * @property mealService a boolean value of whether meal service enabled
 * @property noticeService a boolean value of whether notice service enabled
 * @property pointService a boolean value of whether point service enabled
 * @property studyRoomService a boolean value of whether study room service enabled
 * @property remainService a boolean value of whether remain service enabled
 */
data class FetchAvailableFeaturesOutput(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainService: Boolean,
)
