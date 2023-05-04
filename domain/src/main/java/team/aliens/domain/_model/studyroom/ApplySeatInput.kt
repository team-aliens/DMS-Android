package team.aliens.domain._model.studyroom

import java.util.UUID

data class ApplySeatInput(
    val seatId: UUID,
    val timeSlot: UUID,
)
