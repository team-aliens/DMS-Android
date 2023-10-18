package team.aliens.dms.android.domain._legacy.model.studyroom

import java.util.UUID

data class CancelSeatInput(
    val seatId: UUID,
    val timeSlot: UUID,
)
