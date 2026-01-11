package team.aliens.dms.android.network.meal.datasource

import java.time.LocalDate
import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.meal.apiservice.MealApiService
import team.aliens.dms.android.network.meal.model.FetchMealsResponse
import javax.inject.Inject

internal class NetworkMealDataSourceImpl @Inject constructor(
    private val mealApiService: MealApiService,
) : NetworkMealDataSource() {
    override suspend fun fetchMeals(date: LocalDate): Result<FetchMealsResponse> =
        runCatching { handleNetworkRequest { mealApiService.fetchMeals(date) } }
}
