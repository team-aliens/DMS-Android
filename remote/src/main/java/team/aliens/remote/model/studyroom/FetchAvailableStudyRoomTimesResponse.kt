package team.aliens.remote.model.studyroom

import com.google.gson.annotations.SerializedName
import java.util.*

data class FetchAvailableStudyRoomTimesResponse(
    @SerializedName("time_slots") val timeSlots: List<TimeSlot>,
) {
    data class TimeSlot(
        @SerializedName("id") val id: UUID,
        @SerializedName("start_time") val startTime: String,
        @SerializedName("end_time") val endTime: String,
    )
}
