package team.aliens.domain._model.mypage

import team.aliens.domain._model._common.Sex

/**
 * An entity contains my page information
 * @property schoolName user's school name
 * @property name user's name
 * @property gcn user's grade, class, and number
 * @property profileImageUrl user's profile image url
 * @property sex user's sex
 * @property bonusPoint user's bonus point
 * @property minusPoint user's minus point
 * @property phrase user's phrase related to his/her point
 */
data class MyPage(
    val schoolName: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
    val sex: Sex,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
