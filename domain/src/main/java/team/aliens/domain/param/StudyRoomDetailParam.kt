package team.aliens.domain.param

import java.util.UUID

data class StudyRoomDetailParam(
    val roomId: String,
    val timeSlot: UUID?,
)