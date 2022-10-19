package com.example.local_domain.entity

data class UserVisibleLocalEntity(
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
