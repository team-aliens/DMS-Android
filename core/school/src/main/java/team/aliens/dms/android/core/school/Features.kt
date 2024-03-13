package team.aliens.dms.android.core.school

import team.aliens.dms.android.core.school.network.model.FeaturesResponse

data class Features(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainsService: Boolean,
)

fun FeaturesResponse.toModel(): Features = Features(
    mealService = this.mealService,
    noticeService = this.noticeService,
    pointService = this.pointService,
    studyRoomService = this.studyRoomService,
    remainsService = this.remainsService,
)
