package team.aliens.dms_android.database.datasource

import team.aliens.data.datasource.local.LocalMealDataSource
import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.Meal
import team.aliens.dms_android.database.room.dao.MealDao
import team.aliens.dms_android.database.room.entity.toData
import team.aliens.dms_android.database.room.entity.toDomain
import javax.inject.Inject

class LocalMealDataSourceImpl @Inject constructor(
    private val mealDao: MealDao,
) : LocalMealDataSource {

    override suspend fun fetchMeal(
        input: FetchMealInput,
    ): Meal {
        val date = input.date

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
