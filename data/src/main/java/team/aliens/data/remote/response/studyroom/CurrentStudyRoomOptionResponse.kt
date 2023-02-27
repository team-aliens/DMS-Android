package team.aliens.data.remote.response.studyroom

import com.google.gson.annotations.SerializedName

data class CurrentStudyRoomOptionResponse(
    @SerializedName("floor") val floor: Long,
    @SerializedName("name") val name: String,
)