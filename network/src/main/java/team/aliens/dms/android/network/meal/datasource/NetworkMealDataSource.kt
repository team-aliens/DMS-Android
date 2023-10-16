package team.aliens.dms.android.network.meal.datasource

import org.threeten.bp.LocalDate
import team.aliens.dms.android.network.meal.model.FetchMealsResponse

abstract class NetworkMealDataSource {
    abstract suspend fun fetchMeals(date: LocalDate): FetchMealsResponse
}
