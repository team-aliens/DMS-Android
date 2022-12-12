package com.example.feature_domain.repository

import com.example.feature_domain.entity.MealEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MealRepository {

    suspend fun fetchMealValue(date: LocalDate): Flow<MealEntity>
}