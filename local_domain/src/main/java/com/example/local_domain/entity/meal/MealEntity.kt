package com.example.local_domain.entity.meal

import java.time.LocalDateTime

data class MealEntity(
    val meals: List<MealValues>
) {
    data class MealValues(
        val date: LocalDateTime,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}
