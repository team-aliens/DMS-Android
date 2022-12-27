package com.example.local_domain.repository.meal

import com.example.local_domain.entity.meal.MealEntity
import java.time.LocalDateTime

interface LocalMealRepository {

    suspend fun fetchMealList(date: String): MealEntity
}
