package team.aliens.domain.model.mypage

import team.aliens.domain.model._common.Sex

/**
 * An entity contains my page information
 * @property schoolName user's school name
 * @property name user's name
 * @property gradeClassNumber user's grade, class, and number
 * @property profileImageUrl user's profile image url
 * @property sex user's sex
 * @property bonusPoint user's bonus point
 * @property minusPoint user's minus point
 * @property phrase user's phrase related to his/her point
 */
data class MyPage(
    val schoolName: String,
    val name: String,
    val gradeClassNumber: String,
    val profileImageUrl: String,
    val sex: Sex,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
