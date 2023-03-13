package team.aliens.domain.entity.studyroom

import java.util.*

data class StudyRoomAvailableTimeListEntity(
    val timeSlots: List<AvailableTime>
){
    data class AvailableTime(
        val id: UUID,
        val name: String,
    )
}