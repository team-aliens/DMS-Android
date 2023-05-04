package team.aliens.remote.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput

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
