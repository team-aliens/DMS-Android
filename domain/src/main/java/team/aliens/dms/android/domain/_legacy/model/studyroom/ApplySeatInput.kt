package team.aliens.dms.android.domain._legacy.model.studyroom

import java.util.UUID

data class ApplySeatInput(
    val seatId: UUID,
    val timeSlot: UUID,
)