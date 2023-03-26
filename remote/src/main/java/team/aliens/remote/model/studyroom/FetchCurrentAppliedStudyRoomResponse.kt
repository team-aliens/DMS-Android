package team.aliens.remote.model.studyroom

import com.google.gson.annotations.SerializedName

data class FetchCurrentAppliedStudyRoomResponse(
    @SerializedName("floor") val floor: Int,
    @SerializedName("name") val name: String,
)
