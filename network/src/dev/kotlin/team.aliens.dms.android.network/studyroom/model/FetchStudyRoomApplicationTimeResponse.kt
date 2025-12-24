package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName

data class FetchStudyRoomApplicationTimeResponse(
    @SerializedName("start_at") val startAt: String,
    @SerializedName("end_at") val endAt: String,
)
