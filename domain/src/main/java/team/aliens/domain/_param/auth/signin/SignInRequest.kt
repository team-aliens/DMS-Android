package team.aliens.domain._param.auth.signin

/**
 * @author junsuPark
 * A request, sent when when signing in into service.
 * [accountId] user id
 */
data class SignInRequest(
    val accountId: String,
    val password: String,
)
