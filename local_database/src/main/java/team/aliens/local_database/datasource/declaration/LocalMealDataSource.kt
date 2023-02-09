package team.aliens.local_database.datasource.declaration

import team.aliens.local_database.entity.meal.MealRoomEntity

interface LocalMealDataSource {

    suspend fun setMeal(mealRoomEntity: List<MealRoomEntity>)

    suspend fun fetchMealList(date: String): MealRoomEntity
}