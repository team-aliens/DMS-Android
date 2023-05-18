package team.aliens.domain.model.studyroom

import java.util.UUID

data class CancelSeatInput(
    val seatId: UUID,
    val timeSlot: UUID,
)
