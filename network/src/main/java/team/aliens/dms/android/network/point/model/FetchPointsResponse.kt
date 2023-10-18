package team.aliens.dms.android.network.point.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate
import team.aliens.dms.android.shared.model.PointType
import java.util.UUID

data class FetchPointsResponse(
    @SerializedName("total_point") val totalPoint: Int,
    @SerializedName("point_histories") val pointResponses: List<PointResponse>,
) {
    data class PointResponse(
        @SerializedName("point_history_id") val id: UUID,
        @SerializedName("date") val date: LocalDate,
        @SerializedName("type") val type: PointType, // TODO: String으로 받아보기
        @SerializedName("name") val name: String,
        @SerializedName("score") val score: Int,
    )
}
