package com.example.local_domain.entity

import java.time.LocalDateTime

data class UserVisibleLocalEntity(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime,
)