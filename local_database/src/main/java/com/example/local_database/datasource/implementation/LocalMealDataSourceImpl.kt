package com.example.local_database.datasource.implementation

import com.example.local_database.dao.MealDao
import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.entity.meal.MealRoomEntity
import java.time.LocalDateTime
import javax.inject.Inject

class LocalMealDataSourceImpl @Inject constructor(
    private val mealDao: MealDao,
): LocalMealDataSource {

    override suspend fun setMeal(mealRoomEntity: List<MealRoomEntity>) {
        mealDao.saveMealList(mealRoomEntity)
    }

    override suspend fun fetchMealList(date: String): List<MealRoomEntity> =
        mealDao.fetchMealList(date)
}
