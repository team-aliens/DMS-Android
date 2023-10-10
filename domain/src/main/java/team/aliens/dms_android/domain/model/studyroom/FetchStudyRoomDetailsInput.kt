package team.aliens.dms_android.domain.model.studyroom

import java.util.UUID

data class FetchStudyRoomDetailsInput(
    val studyRoomId: UUID,
    val timeSlot: UUID,
)
