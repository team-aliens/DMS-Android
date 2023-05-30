package team.aliens.remote.model.point

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model._common.PointType
import team.aliens.domain.model.point.FetchPointsOutput
import java.util.UUID

data class FetchPointsResponse(
    @SerializedName("total_point") val totalPoint: Int,
    @SerializedName("point_histories") val pointResponses: List<PointResponse>,
) {
    data class PointResponse(
        @SerializedName("point_history_id") val id: UUID,
        @SerializedName("date") val date: String,
        @SerializedName("type") val type: String,
        @SerializedName("name") val name: String,
        @SerializedName("score") val score: Int,
    )
}

internal fun FetchPointsResponse.toDomain(): FetchPointsOutput {
    return FetchPointsOutput(
        totalPoint = this.totalPoint,
        pointHistories = this.pointResponses.toDomain(),
    )
}

internal fun FetchPointsResponse.PointResponse.toDomain(): FetchPointsOutput.PointInformation {
    return FetchPointsOutput.PointInformation(
        id = this.id,
        date = this.date,
        type = PointType.valueOf(this.type),
        name = this.name,
        score = this.score,
    )
}

internal fun List<FetchPointsResponse.PointResponse>.toDomain(): List<FetchPointsOutput.PointInformation> {
    return this.map(FetchPointsResponse.PointResponse::toDomain)
}
