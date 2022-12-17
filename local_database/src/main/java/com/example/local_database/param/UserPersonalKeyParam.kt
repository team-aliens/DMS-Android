package com.example.local_database.param

import com.example.local_domain.entity.UserVisibleLocalEntity
import java.time.LocalDateTime

data class UserPersonalKeyParam(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime,
)

fun UserPersonalKeyParam.toDbEntity() =
    UserVisibleLocalEntity(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
    )