package team.aliens.local.datasource

import team.aliens.data._datasource.local.LocalMealDataSource
import team.aliens.domain._model.meal.Meal
import team.aliens.local.room.dao.MealDao
import team.aliens.local.room.entity.toData
import team.aliens.local.room.entity.toDomain
import javax.inject.Inject

class LocalMealDataSourceImpl @Inject constructor(
    private val mealDao: MealDao,
) : LocalMealDataSource {

    override suspend fun fetchMeal(
        date: String,
    ): Meal {
        return mealDao.findByDate(
            date = date,
        ).toDomain()
    }

    override suspend fun saveMeal(
        meal: Meal,
    ) {
        mealDao.saveOne(
            mealEntity = meal.toData(),
        )
    }

    override suspend fun saveMeals(
        vararg meals: Meal,
    ) {
        mealDao.saveAll(
            mealEntities = arrayOf(*meals).toData(),
        )
    }
}
