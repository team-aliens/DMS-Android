package team.aliens.dms.android.database.meal.datasource

import team.aliens.dms.android.core.database.entity.MealEntity
import java.time.LocalDate

abstract class DatabaseMealDataSource {
    abstract suspend fun queryMeal(date: LocalDate): MealEntity
    abstract suspend fun saveMeal(meal: MealEntity)
    abstract suspend fun saveMeals(meals: List<MealEntity>)
}
