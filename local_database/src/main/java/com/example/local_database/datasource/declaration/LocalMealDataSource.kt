package com.example.local_database.datasource.declaration

import com.example.local_database.entity.meal.MealRoomEntity

interface LocalMealDataSource {

    suspend fun setMeal(mealRoomEntity: MealRoomEntity)

    suspend fun fetchMealList(): MealRoomEntity
}