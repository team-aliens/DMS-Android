package com.example.dms_android.feature.home.data

import java.time.LocalDateTime

data class MealRvData(
    val date: LocalDateTime,
    val breakfast: List<String>,
    val lunch: List<String>,
    val dinner: List<String>,
)
