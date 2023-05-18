package team.aliens.data.datasource.remote

import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput

interface RemoteMealDataSource {

    suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput
}
