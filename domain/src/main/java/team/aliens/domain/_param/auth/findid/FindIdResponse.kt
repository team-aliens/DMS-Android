package team.aliens.domain._param.auth.findid

/**
 * @author junsuPark
 * A response, returned while finding user's id.
 * [email] user's email, which the user's id was sent
 */
data class FindIdResponse(
    val email: String,
)