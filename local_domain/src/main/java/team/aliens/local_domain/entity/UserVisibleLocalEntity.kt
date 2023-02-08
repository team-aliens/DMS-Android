package team.aliens.local_domain.entity

import org.threeten.bp.LocalDateTime

data class UserVisibleLocalEntity(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime,
)