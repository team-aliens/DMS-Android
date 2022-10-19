package com.example.local_database.param

import com.example.local_domain.entity.UserVisibleLocalEntity

data class UserVisibleParam(
    val accessToken: String,
    val expiredAt: String,
    val refreshToken: String,
    val features: FeaturesParam,
) {
    data class FeaturesParam(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
    )
}

fun UserVisibleParam.toDbEntity() =
    UserVisibleLocalEntity(
        accessToken = accessToken,
        expiredAt = expiredAt,
        refreshToken = refreshToken,
        features = features.toDbEntity()
    )

fun UserVisibleParam.FeaturesParam.toDbEntity() =
    UserVisibleLocalEntity.FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )
