package team.aliens.domain._param.student

/**
 * @author junsuPark
 * A response, returned while finding user's id
 * @property email user's email, which the user's id was sent
 */
data class FindIdResponse(
    val email: String,
)