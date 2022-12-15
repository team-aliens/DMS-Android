package com.example.local_database.param

import com.example.local_domain.entity.UserVisibleLocalEntity

data class UserPersonalKeyParam(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
)

fun UserPersonalKeyParam.toDbEntity() =
    UserVisibleLocalEntity(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
    )