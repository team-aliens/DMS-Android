package com.example.local_domain.entity.meal

data class MealEntity(
    val date: String,
    val breakfast: List<String>,
    val lunch: List<String>,
    val dinner: List<String>,
)
