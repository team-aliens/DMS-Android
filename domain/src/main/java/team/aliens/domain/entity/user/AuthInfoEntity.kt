package team.aliens.domain.entity.user

import org.threeten.bp.LocalDateTime

data class AuthInfoEntity(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime,
    val features: Features,
) {
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
    )
}

