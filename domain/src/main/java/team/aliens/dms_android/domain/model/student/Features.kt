package team.aliens.dms_android.domain.model.student

/**
 * A data class of app feature
 * @property mealService boolean value of meal service enabled
 * @property noticeService boolean value of notice service enabled
 * @property pointService boolean value of point service enabled
 * @property studyRoomService boolean value of study room service enabled
 * @property remainsService boolean value of remains service enabled
 */
data class Features(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainsService: Boolean,
) {
    companion object {
        fun trueInitialized() = Features(
            mealService = true,
            noticeService = true,
            pointService = true,
            studyRoomService = true,
            remainsService = true,
        )

        fun falseInitialized() = Features(
            mealService = false,
            noticeService = false,
            pointService = false,
            studyRoomService = false,
            remainsService = false,
        )
    }
}
