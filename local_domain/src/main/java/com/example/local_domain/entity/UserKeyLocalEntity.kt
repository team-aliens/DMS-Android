package com.example.local_domain.entity

data class UserKeyLocalEntity(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)
