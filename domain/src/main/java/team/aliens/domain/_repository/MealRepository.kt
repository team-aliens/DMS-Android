package team.aliens.domain._repository

import team.aliens.domain._model.meal.FetchMealsOutput

interface MealRepository {

    suspend fun queryMeals(
        date: String,
    ): FetchMealsOutput
}
