package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemoteMealDataSource
import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput
import team.aliens.network.model.meal.toDomain
import team.aliens.network.apiservice.MealApiService
import team.aliens.network.util.sendHttpRequest
import javax.inject.Inject

class RemoteMealDataSourceImpl @Inject constructor(
    private val mealApiService: MealApiService,
) : RemoteMealDataSource {

    override suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput {
        return sendHttpRequest {
            mealApiService.fetchMeals(
                date = input.date,
            )
        }.toDomain()
    }
}
