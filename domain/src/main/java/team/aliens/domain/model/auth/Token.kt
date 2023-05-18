package team.aliens.domain.model.auth

/**
 * A token entity contains access and refresh tokens, and their expiration
 * @property accessToken an access token
 * @property accessTokenExpiredAt an expiration time of access token
 * @property refreshToken a refresh token
 * @property refreshTokenExpiredAt an expiration time of refresh token
 */
data class Token(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
)
