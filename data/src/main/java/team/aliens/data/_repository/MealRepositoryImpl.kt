package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteMealDataSource
import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.domain._repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteMealDataSource: RemoteMealDataSource,
) : MealRepository {

    override suspend fun fetchMeals(
        date: String,
    ): FetchMealsOutput {
        return remoteMealDataSource.fetchMeals(
            date = date,
        )
    }
}
