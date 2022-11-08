package com.example.local_database.param

import com.example.local_domain.entity.UserVisibleLocalEntity

data class UserPersonalKeyParam(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)

fun UserPersonalKeyParam.toDbEntity() =
    UserVisibleLocalEntity(
        accessToken = accessToken,
        expiredAt = expiredAt,
        refreshToken = refreshToken,
    )