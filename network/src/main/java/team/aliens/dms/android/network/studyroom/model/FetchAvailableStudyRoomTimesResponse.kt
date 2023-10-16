package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchAvailableStudyRoomTimesResponse(
    @SerializedName("time_slots") val timeSlots: List<TimeSlotResponse>,
) {
    data class TimeSlotResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("start_time") val startTime: String,
        @SerializedName("end_time") val endTime: String,
    )
}
