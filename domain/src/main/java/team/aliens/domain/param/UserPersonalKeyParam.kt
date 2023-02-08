package team.aliens.domain.param

data class UserPersonalKeyParam(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)
