package com.example.domain.entity.mypage

import com.example.domain.enums.PointType
import java.util.UUID

data class PointListEntity(
    val totalPoint: Int,
    val pointValue: List<PointValue>
) {
    data class PointValue(
        val pointId: String,
        val date: String,
        val pointType: PointType,
        val name: String,
        val score: Int,
    )
}
