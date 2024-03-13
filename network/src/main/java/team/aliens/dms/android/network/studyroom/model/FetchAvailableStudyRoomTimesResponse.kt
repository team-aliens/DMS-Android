package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchAvailableStudyRoomTimesResponse(
    @SerializedName("time_slots") val availableStudyRoomTimes: List<AvailableStudyRoomTimeResponse>,
) {
    data class AvailableStudyRoomTimeResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("start_time") val startTime: String,
        @SerializedName("end_time") val endTime: String,
    )
}
