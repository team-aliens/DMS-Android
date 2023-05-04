package team.aliens.domain._model.studyroom

import java.util.UUID

data class FetchStudyRoomDetailsInput(
    val studyRoomId: UUID,
    val timeSlot: UUID,
)
