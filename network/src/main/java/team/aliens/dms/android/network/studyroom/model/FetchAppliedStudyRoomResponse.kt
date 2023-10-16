package team.aliens.dms.android.network.studyroom.model

import com.google.gson.annotations.SerializedName

data class FetchAppliedStudyRoomResponse(
    @SerializedName("floor") val floor: Int,
    @SerializedName("name") val name: String,
)
