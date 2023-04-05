package team.aliens.local_database.param

import team.aliens.local_domain.entity.notice.UserVisibleInformEntity

data class UserVisibleParam(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainService: Boolean,
)

fun UserVisibleParam.toDbEntity() = UserVisibleInformEntity(
    mealService = mealService,
    noticeService = noticeService,
    pointService = pointService,
    studyRoomService = studyRoomService,
    remainService = remainService,
)
