package com.example.local_domain.entity

data class UserVisibleLocalEntity(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
)