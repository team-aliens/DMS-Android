package team.aliens.dms.android.core.jwt

import org.threeten.bp.LocalDateTime

data class AccessToken(
    val value: String,
    val expiration: LocalDateTime,
)

val Tokens.accessToken: AccessToken
    inline get() = AccessToken(
        value = this.accessToken,
        expiration = this.accessTokenExpiration,
    )
