package com.example.local_domain.entity

data class UserVisibleLocalEntity(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)