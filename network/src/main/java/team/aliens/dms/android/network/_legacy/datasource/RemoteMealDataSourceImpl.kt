package team.aliens.dms.android.network._legacy.datasource

import team.aliens.dms.android.data.datasource.remote.RemoteMealDataSource
import team.aliens.dms.android.network._legacy.apiservice.MealApiService
import team.aliens.dms.android.network.model.meal.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.domain.model.meal.FetchMealsInput
import team.aliens.dms.android.domain.model.meal.FetchMealsOutput
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
