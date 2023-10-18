package team.aliens.dms.android.domain._legacy.model.studyroom

import java.util.UUID

data class FetchStudyRoomDetailsInput(
    val studyRoomId: UUID,
    val timeSlot: UUID,
)
