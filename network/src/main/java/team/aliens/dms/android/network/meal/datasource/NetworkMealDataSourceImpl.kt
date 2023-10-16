package team.aliens.dms.android.network.meal.datasource

import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.meal.apiservice.MealApiService
import team.aliens.dms.android.network.meal.model.FetchMealsResponse
import javax.inject.Inject

internal class NetworkMealDataSourceImpl @Inject constructor(
    private val mealApiService: MealApiService,
) : NetworkMealDataSource() {
    override suspend fun fetchMeals(date: LocalDate): FetchMealsResponse =
        sendHttpRequest { mealApiService.fetchMeals(date) }
}
