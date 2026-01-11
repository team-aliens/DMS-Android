package team.aliens.dms.android.network.meal.datasource

import java.time.LocalDate
import team.aliens.dms.android.network.meal.model.FetchMealsResponse

abstract class NetworkMealDataSource {
    abstract suspend fun fetchMeals(date: LocalDate): Result<FetchMealsResponse>
}
