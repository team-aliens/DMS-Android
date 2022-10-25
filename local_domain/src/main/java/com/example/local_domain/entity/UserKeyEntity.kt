package com.example.local_domain.entity

data class UserKeyEntity(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)
