package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteMealDataSource
import team.aliens.domain._model.meal.FetchMealsOutput

class RemoteMealDataSourceImpl : RemoteMealDataSource {

    override suspend fun fetchMeals(
        date: String,
    ): FetchMealsOutput {
        TODO("Not yet implemented")
    }
}
