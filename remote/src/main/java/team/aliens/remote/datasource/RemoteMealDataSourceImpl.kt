package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteMealDataSource
import team.aliens.domain._model.meal.FetchMealsInput
import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.remote.model.meal.toDomain
import team.aliens.remote.apiservice.MealApiService
import team.aliens.remote.util.sendHttpRequest
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
