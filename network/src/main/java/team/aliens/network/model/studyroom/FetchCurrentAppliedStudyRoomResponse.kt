package team.aliens.network.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.studyroom.FetchCurrentAppliedStudyRoomOutput

data class FetchCurrentAppliedStudyRoomResponse(
    @SerializedName("floor") val floor: Int,
    @SerializedName("name") val name: String,
)

internal fun FetchCurrentAppliedStudyRoomResponse.toDomain(): FetchCurrentAppliedStudyRoomOutput {
    return FetchCurrentAppliedStudyRoomOutput(
        floor = this.floor,
        name = this.name,
    )
}
