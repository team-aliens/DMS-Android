package com.example.data.remote.response.mypage

import com.example.domain.entity.mypage.PointListEntity
import com.example.domain.enums.PointType
import com.google.gson.annotations.SerializedName
import java.util.UUID

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

fun FetchPointListResponse.PointList.toEntity() =
    PointListEntity.PointValue(
        pointId = pointId.toString(),
        date = date,
        pointType = pointType,
        name = name,
        score = score,
    )

fun FetchPointListResponse.toEntity() =
    PointListEntity(
        totalPoint = totalPoint,
        pointValue = pointList.map { it.toEntity() },
    )
