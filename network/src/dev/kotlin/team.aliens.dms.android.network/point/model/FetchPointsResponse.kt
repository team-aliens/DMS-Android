package team.aliens.dms.android.network.point.model

import com.google.gson.annotations.SerializedName
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
