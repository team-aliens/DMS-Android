package team.aliens.dms.android.core.jwt

import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.jwt.network.model.TokensResponse

data class Tokens(
    val accessToken: String,
    val accessTokenExpiration: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiration: LocalDateTime,
)

internal fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = this.accessToken,
    accessTokenExpiration = this.accessTokenExpiration,
    refreshToken = this.refreshToken,
    refreshTokenExpiration = this.refreshTokenExpiration,
)
