package team.aliens.local_database.datasource.implementation

import team.aliens.local_database.dao.MealDao
import team.aliens.local_database.datasource.declaration.LocalMealDataSource
import team.aliens.local_database.entity.meal.MealRoomEntity
import javax.inject.Inject

class LocalMealDataSourceImpl @Inject constructor(
    private val mealDao: MealDao,
) : LocalMealDataSource {

    override suspend fun setMeal(mealRoomEntity: List<MealRoomEntity>) {
        mealDao.saveMealList(mealRoomEntity)
    }

    override suspend fun fetchMealList(date: String): MealRoomEntity {
        val response = mealDao.fetchMealList(date)
        return response
    }
}
