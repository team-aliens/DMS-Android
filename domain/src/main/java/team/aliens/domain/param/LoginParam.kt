package team.aliens.domain.param

data class LoginParam(
    val id: String,
    val password: String,
    val autoLogin: Boolean,
)
