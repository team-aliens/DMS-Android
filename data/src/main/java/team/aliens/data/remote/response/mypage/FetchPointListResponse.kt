package team.aliens.data.remote.response.mypage

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.mypage.PointListEntity
import team.aliens.domain.enums.PointType
import java.util.*

data class FetchPointListResponse(
    @SerializedName("total_point") val totalPoint: Int,
    @SerializedName("points") val pointList: List<PointList>,
) {
    data class PointList(
        @SerializedName("point_id") val pointId: UUID,
        @SerializedName("date") val date: String,
        @SerializedName("type") val pointType: PointType,
        @SerializedName("name") val name: String,
        @SerializedName("score") val score: Int,
    )
}

fun FetchPointListResponse.PointList.toEntity() = PointListEntity.PointValue(
    pointId = pointId.toString(),
    date = date,
    pointType = pointType,
    name = name,
    score = score,
)

fun FetchPointListResponse.toEntity() = PointListEntity(
    totalPoint = totalPoint,
    pointValue = pointList.map { it.toEntity() },
)
