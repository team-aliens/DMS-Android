package team.aliens.domain.param

import java.util.UUID

data class ApplyStudyRoomParam(
    val seatId: String,
    val timeSlot: UUID,
)