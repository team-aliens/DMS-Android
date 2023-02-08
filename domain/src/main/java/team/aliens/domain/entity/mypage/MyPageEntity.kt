package team.aliens.domain.entity.mypage

data class MyPageEntity(
    val schoolName: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
