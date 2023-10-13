package team.aliens.dms_android.database.datasource

import team.aliens.dms_android.core.database.dao.MealDao
import team.aliens.dms_android.data.datasource.local.LocalMealDataSource
import team.aliens.dms_android.database.room.entity.toData
import team.aliens.dms_android.database.room.entity.toDomain
import team.aliens.dms.android.domain.model.meal.FetchMealInput
import team.aliens.dms.android.domain.model.meal.Meal
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
        mealDao.save(
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
