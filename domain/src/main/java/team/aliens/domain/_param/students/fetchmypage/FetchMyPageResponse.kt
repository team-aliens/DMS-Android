package team.aliens.domain._param.students.fetchmypage

import team.aliens.domain._param._common.Sex

/**
 * @author junsuPark
 * A response of fetching my page
 * [schoolName] the user's school name
 * [name] the user's name
 * [gcn] the user's grade, class and number
 * [profileImageUrl] the user's profile image url
 * [sex] the user's sex
 * [bonusPoint] the user's total bonus point
 * [minusPoint] the user's total minus point
 * [phrase] the phrase of user's school
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
