package com.example.auth_domain.param

data class UserPersonalKeyParam(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)
