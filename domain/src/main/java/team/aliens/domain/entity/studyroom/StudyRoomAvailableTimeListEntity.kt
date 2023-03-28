package team.aliens.domain.entity.studyroom

import java.util.UUID

data class StudyRoomAvailableTimeListEntity(
    val timeSlots: List<AvailableTime>
){
    data class AvailableTime(
        val id: UUID,
        val startTime: String,
        val endTime: String,
    )
}