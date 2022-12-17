package com.example.auth_domain.entity

import java.time.LocalDateTime

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

