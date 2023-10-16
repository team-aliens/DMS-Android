package team.aliens.dms.android.core.jwt

import team.aliens.dms.android.core.jwt.network.model.TokensResponse

data class Tokens(
    val accessToken: AccessToken,
    val accessTokenExpiration: AccessTokenExpiration,
    val refreshToken: RefreshToken,
    val refreshTokenExpiration: RefreshTokenExpiration,
)

internal fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = this.accessToken,
    accessTokenExpiration = this.accessTokenExpiration,
    refreshToken = this.refreshToken,
    refreshTokenExpiration = this.refreshTokenExpiration,
)
