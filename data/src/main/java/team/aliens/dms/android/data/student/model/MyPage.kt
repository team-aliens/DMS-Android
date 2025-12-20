package team.aliens.dms.android.data.student.model

import team.aliens.dms.android.shared.model.Sex

data class MyPage(
    val schoolName: String = "",
    val name: String = "",
    val gcn: String = "",
    val profileImageUrl: String? = null,
    val sex: Sex = Sex.ALL,
    val bonusPoint: Int = 0,
    val minusPoint: Int = 0,
    val phrase: String = "",
)
