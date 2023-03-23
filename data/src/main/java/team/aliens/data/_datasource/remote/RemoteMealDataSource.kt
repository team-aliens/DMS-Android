package team.aliens.data._datasource.remote

import team.aliens.domain._model.meal.FetchMealsOutput

interface RemoteMealDataSource {

    suspend fun fetchMeals(
        date: String,
    ): FetchMealsOutput
}
