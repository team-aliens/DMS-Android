package com.example.feature_domain.entity

import java.time.LocalDateTime

data class MealEntity(
    val meals: List<MealsValue>,
) {
    data class MealsValue(
        val date: LocalDateTime,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}