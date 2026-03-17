package team.aliens.dms.android.network.meal.datasource

import java.time.LocalDate
import team.aliens.dms.android.network.meal.apiservice.MealApiService
import team.aliens.dms.android.network.meal.model.FetchMealsResponse
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import javax.inject.Inject

internal class NetworkMealDataSourceImpl @Inject constructor(
    private val mealApiService: MealApiService,
) : NetworkMealDataSource() {
    override suspend fun fetchMeals(date: LocalDate): Result<FetchMealsResponse> =
        suspendRunCatching { mealApiService.fetchMeals(date) }
}
