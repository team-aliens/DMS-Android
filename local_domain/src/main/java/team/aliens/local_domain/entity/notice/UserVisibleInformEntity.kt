package team.aliens.local_domain.entity.notice

data class UserVisibleInformEntity(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainService: Boolean,
)