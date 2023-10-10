package team.aliens.dms_android.domain.model.auth

/**
 * A response, received when checking user's id
 * @property email an email, where user's id has sent
 */
data class CheckIdExistsOutput(
    val email: String,
)