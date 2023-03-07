package team.aliens.domain._param.auth.signin

data class SignInRequest(
    val accountId: String,
    val password: String,
)
