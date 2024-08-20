package team.aliens.dms.android.data.student.model

import team.aliens.dms.android.shared.model.Sex

data class MyPage(
    val schoolName: String,
    val studentName: String,
    val gradeClassNumber: String,
    val profileImageUrl: String,
    val sex: Sex,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
