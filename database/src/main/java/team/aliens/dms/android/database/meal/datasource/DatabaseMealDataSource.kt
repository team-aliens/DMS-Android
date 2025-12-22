package team.aliens.dms.android.database.meal.datasource

import java.time.LocalDate
import team.aliens.dms.android.core.database.entity.MealEntity

abstract class DatabaseMealDataSource {
    abstract suspend fun queryMeal(date: LocalDate): MealEntity
    abstract suspend fun saveMeal(meal: MealEntity)
    abstract suspend fun saveMeals(meals: List<MealEntity>)
}
