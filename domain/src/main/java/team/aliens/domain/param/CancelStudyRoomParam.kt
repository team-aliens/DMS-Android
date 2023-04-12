package team.aliens.domain.param

import java.util.*

data class CancelStudyRoomParam(
    val seatId: String,
    val timeSlot: UUID,
)
