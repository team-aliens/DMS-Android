package team.aliens.data.datasource.remote

import team.aliens.domain._model.meal.FetchMealsInput
import team.aliens.domain._model.meal.FetchMealsOutput

interface RemoteMealDataSource {

    suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput
}
