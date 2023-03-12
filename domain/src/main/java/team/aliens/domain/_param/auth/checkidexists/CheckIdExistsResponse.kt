package team.aliens.domain._param.auth.checkidexists

/**
 * @author junsuPark
 * A response, received when checking user's id
 * @property email an email, where user's id has sent
 */
data class CheckIdExistsResponse(
    val email: String,
)
