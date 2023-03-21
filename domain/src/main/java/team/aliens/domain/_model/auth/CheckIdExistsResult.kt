package team.aliens.domain._model.auth

/**
 * @author junsuPark
 * A response, received when checking user's id
 * @property email an email, where user's id has sent
 */
data class CheckIdExistsResult(
    val email: String,
)
