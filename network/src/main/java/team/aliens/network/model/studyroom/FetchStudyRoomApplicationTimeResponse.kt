package team.aliens.network.model.studyroom

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput

data class FetchStudyRoomApplicationTimeResponse(
    @SerializedName("start_at") val startAt: String,
    @SerializedName("end_at") val endAt: String,
)

internal fun FetchStudyRoomApplicationTimeResponse.toDomain(): FetchStudyRoomApplicationTimeOutput {
    return FetchStudyRoomApplicationTimeOutput(
        startAt = this.startAt,
        endAt = this.endAt,
    )
}
