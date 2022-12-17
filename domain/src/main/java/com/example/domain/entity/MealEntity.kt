package com.example.domain.entity

data class MealEntity(
    val meals: List<MealsValue>,
) {
    data class MealsValue(
        val date: String,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}