package team.aliens.dms.android.data.meal.repository

import java.time.LocalDate
import team.aliens.dms.android.data.meal.model.Meal

abstract class MealRepository {

    abstract suspend fun fetchMeal(date: LocalDate): Meal

    abstract suspend fun updateMeal(date: LocalDate): Meal
}
