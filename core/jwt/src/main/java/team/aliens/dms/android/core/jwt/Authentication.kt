package team.aliens.dms.android.core.jwt

data class Authentication(
    val accessToken: String,
    val accessTokenExpiration: String,
    val refreshToken: String,
    val refreshTokenExpiration: String,
)
