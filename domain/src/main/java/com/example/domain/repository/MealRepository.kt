package com.example.domain.repository

import com.example.domain.entity.MealEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface MealRepository {

    suspend fun fetchMealValue(date: LocalDate): MealEntity
}