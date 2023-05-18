package team.aliens.domain.model.studyroom

import java.util.UUID

data class ApplySeatInput(
    val seatId: UUID,
    val timeSlot: UUID,
)
