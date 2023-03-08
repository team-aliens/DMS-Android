package team.aliens.domain._param.auth.signup

/**
 * @author junsuPark
 * A response, received when checking user's id
 * [email] an email, where user's id has sent
 */
data class CheckIdExistsResponse(
    val email: String,
)
