package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemoteMealDataSource
import team.aliens.dms_android.network.apiservice.MealApiService
import team.aliens.dms_android.network.model.meal.toDomain
import team.aliens.dms_android.network.util.sendHttpRequest
import team.aliens.dms_android.domain.model.meal.FetchMealsInput
import team.aliens.dms_android.domain.model.meal.FetchMealsOutput
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
