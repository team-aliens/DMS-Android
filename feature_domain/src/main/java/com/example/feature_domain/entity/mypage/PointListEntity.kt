package com.example.feature_domain.entity.mypage

import com.example.feature_domain.enums.PointType
import java.util.UUID

data class PointListEntity(
    val totalPoint: Int,
    val pointValue: List<PointValue>
) {
    data class PointValue(
        val pointId: UUID,
        val date: String,
        val pointType: PointType,
        val name: String,
        val score: Int,
    )
}
