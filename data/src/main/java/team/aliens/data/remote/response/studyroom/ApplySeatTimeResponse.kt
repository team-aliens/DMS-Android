package team.aliens.data.remote.response.studyroom

import com.google.gson.annotations.SerializedName

data class ApplySeatTimeResponse(
    @SerializedName("end_at") val endAt: String,
    @SerializedName("start_at") val startAt: String,
)