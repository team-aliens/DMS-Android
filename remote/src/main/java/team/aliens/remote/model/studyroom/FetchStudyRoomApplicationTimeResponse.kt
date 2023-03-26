package team.aliens.remote.model.studyroom

import com.google.gson.annotations.SerializedName

data class FetchStudyRoomApplicationTimeResponse(
    @SerializedName("start_at") val startAt: String,
    @SerializedName("end_at") val endAt: String,
)
