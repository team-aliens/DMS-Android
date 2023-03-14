package team.aliens.data.remote.response.studyroom

import com.google.gson.annotations.SerializedName
import java.util.*

data class StudyRoomAvailableTimeListResponse(
    @SerializedName("time_slots") val timeSlots: List<AvailableTime>,
){
    data class AvailableTime(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
    )
}