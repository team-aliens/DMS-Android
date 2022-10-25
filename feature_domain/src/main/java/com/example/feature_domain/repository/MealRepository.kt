package com.example.feature_domain.repository

import com.example.feature_domain.entity.MealEntity
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun fetchMealValue(date: String): Flow<MealEntity>
}