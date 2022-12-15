package com.example.auth_domain.entity

data class AuthInfoEntity(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val features: Features,
) {
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
    )
}

