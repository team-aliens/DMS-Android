package com.example.feature_data.remote.datasource.declaration

import com.example.feature_data.remote.response.meal.MealResponse

interface RemoteMealDataSource {

    suspend fun getMealValue(date: String): MealResponse
}