package team.aliens.dms.android.core.jwt

import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.jwt.network.model.TokensResponse
import team.aliens.dms.android.shared.date.toLocalDateTime
import team.aliens.dms.android.shared.date.util.now

data class Tokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)

sealed class Token {
    abstract val value: String
    abstract val expiration: LocalDateTime

    fun isExpired(then: LocalDateTime = now): Boolean = then.isAfter(expiration)
}

data class AccessToken(
    override val value: String,
    override val expiration: LocalDateTime,
) : Token()

data class RefreshToken(
    override val value: String,
    override val expiration: LocalDateTime,
) : Token()

internal fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = this.accessTokenExpiration.toLocalDateTime(),
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = this.refreshTokenExpiration.toLocalDateTime(),
    ),
)
