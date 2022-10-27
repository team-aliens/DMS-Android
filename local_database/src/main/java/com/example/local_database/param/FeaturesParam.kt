package com.example.local_database.param

import com.example.local_domain.param.FeaturesVisibleParam

data class FeaturesParam(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
)

fun FeaturesParam.toDbEntity() =
    FeaturesVisibleParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )
