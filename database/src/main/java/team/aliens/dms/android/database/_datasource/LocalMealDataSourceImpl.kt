package team.aliens.dms.android.database._datasource
/*

import team.aliens.dms.android.core.database.dao.MealDao
import team.aliens.dms.android.data.datasource.local.LocalMealDataSource
import team.aliens.dms.android.database.model.mapper.toData
import team.aliens.dms.android.database.model.mapper.toDomain
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
*/
