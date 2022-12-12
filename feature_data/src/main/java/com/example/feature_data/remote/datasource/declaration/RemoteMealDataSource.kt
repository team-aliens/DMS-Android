package com.example.feature_data.remote.datasource.declaration

import com.example.feature_data.remote.response.meal.MealResponse
import java.time.LocalDate

interface RemoteMealDataSource {

    suspend fun getMealValue(date: LocalDate): MealResponse
}