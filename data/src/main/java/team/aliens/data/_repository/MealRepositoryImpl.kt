package team.aliens.data._repository

import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.domain._repository.MealRepository

class MealRepositoryImpl(
    // private val remoteMealDataSource: RemoteMealDataSource,
) : MealRepository {

    override suspend fun fetchMeals(
        date: String,
    ): FetchMealsOutput {
        TODO("Not yet implemented")
    }
}
