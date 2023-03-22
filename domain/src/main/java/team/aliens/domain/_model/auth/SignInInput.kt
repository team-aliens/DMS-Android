package team.aliens.domain._model.auth

/**
 * A request, sent when when signing in into service
 * @property accountId user id
 */
data class SignInInput(
    val accountId: String,
    val password: String,
)
