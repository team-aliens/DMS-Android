package team.aliens.domain._param.student.fetchmypage

import team.aliens.domain._common.Sex

/**
 * @author junsuPark
 * A response of fetching my page
 * @property schoolName the user's school name
 * @property name the user's name
 * @property gcn the user's grade, class and number
 * @property profileImageUrl the user's profile image url
 * @property sex the user's sex
 * @property bonusPoint the user's total bonus point
 * @property minusPoint the user's total minus point
 * @property phrase the phrase of user's school
 */
data class FetchMyPageResponse(
    val schoolName: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
    val sex: Sex,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
