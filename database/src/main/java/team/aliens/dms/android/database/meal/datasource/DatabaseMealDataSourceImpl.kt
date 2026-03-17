package team.aliens.dms.android.database.meal.datasource

import team.aliens.dms.android.core.database.dao.MealDao
import team.aliens.dms.android.core.database.entity.MealEntity
import java.time.LocalDate
import javax.inject.Inject

internal class DatabaseMealDataSourceImpl @Inject constructor(
    private val mealDao: MealDao,
) : DatabaseMealDataSource() {
    override suspend fun queryMeal(date: LocalDate): MealEntity {
        return mealDao.findByDate(date)
    }

    override suspend fun saveMeal(meal: MealEntity) {
        mealDao.save(meal)
    }

    override suspend fun saveMeals(meals: List<MealEntity>) {
        mealDao.saveAll(meals)
    }
}
