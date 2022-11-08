package com.example.local_database.param

import com.example.local_domain.entity.UserVisibleLocalLocalEntity

data class UserPersonalKeyParam(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
)

fun UserPersonalKeyParam.toDbEntity() =
    UserVisibleLocalLocalEntity(
        accessToken = accessToken,
        expiredAt = expiredAt,
        refreshToken = refreshToken,
    )