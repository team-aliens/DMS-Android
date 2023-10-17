package team.aliens.dms.android.data.meal.repository

import org.threeten.bp.LocalDate
import team.aliens.dms.android.data.meal.model.Meal

abstract class MealRepository {

    abstract fun fetchMeal(date: LocalDate): Meal
}
