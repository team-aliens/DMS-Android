package team.aliens.domain._repository

import team.aliens.domain._model.meal.QueryMealsResult

interface MealRepository {

    suspend fun queryMeals(
        date: String,
    ): QueryMealsResult
}
