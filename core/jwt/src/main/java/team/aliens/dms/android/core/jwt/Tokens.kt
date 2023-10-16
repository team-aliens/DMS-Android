package team.aliens.dms.android.core.jwt

data class Tokens(
    val accessToken: AccessToken,
    val accessTokenExpiration: AccessTokenExpiration,
    val refreshToken: RefreshToken,
    val refreshTokenExpiration: RefreshTokenExpiration,
)
