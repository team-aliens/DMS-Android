package team.aliens.domain._model.studyroom

import java.util.UUID

data class CancelSeatInput(
    val seatId: UUID,
    val timeSlot: UUID,
)
