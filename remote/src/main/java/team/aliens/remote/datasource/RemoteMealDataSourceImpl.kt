package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteMealDataSource
import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.remote.model.meal.toDomain
import team.aliens.remote.service.MealService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteMealDataSourceImpl @Inject constructor(
    private val mealService: MealService,
) : RemoteMealDataSource {

    override suspend fun fetchMeals(
        date: String,
    ): FetchMealsOutput {
        return sendHttpRequest {
            mealService.fetchMeals(
                date = date,
            )
        }.toDomain()
    }
}
