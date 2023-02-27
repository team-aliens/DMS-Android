package team.aliens.domain.entity.mypage

import team.aliens.domain.enums.PointType

data class PointListEntity(
    val totalPoint: Int,
    val pointValue: List<PointValue>,
) {
    data class PointValue(
        val date: String,
        val pointType: PointType,
        val name: String,
        val score: Int,
    )
}
