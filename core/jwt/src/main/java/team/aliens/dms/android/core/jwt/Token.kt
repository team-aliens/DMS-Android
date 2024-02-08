package team.aliens.dms.android.core.jwt

import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.jwt.network.model.TokensResponse

data class Tokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)

sealed class Token

data class AccessToken(
    val value: String,
    val expiration: LocalDateTime,
) : Token()

data class RefreshToken(
    val value: String,
    val expiration: LocalDateTime,
) : Token()


internal fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = this.accessTokenExpiration,
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = this.refreshTokenExpiration,
    ),
)
