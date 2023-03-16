package team.aliens.domain._entity.mypage

import team.aliens.domain._common.Sex

data class MyPageEntity(
    val schoolName: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
    val sex: Sex,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
