package team.aliens.dms.android.database.datasource

import team.aliens.dms.android.domain.model.meal.FetchMealInput
import team.aliens.dms.android.domain.model.meal.Meal

abstract class DatabaseMealDataSource {
    abstract suspend fun queryMeal(input: FetchMealInput): Meal
    abstract suspend fun saveMeal(meal: Meal)
    abstract suspend fun saveMeals(vararg meals: Meal)
}
