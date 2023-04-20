package team.aliens.domain._repository

import team.aliens.domain._model.meal.FetchMealsInput
import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.domain._model.meal.Meal

interface MealRepository {

    suspend fun fetchMeal(
        date: String,
    ): Meal

    suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput

    suspend fun saveMeal(
        meal: Meal,
    )

    suspend fun saveMeals(
        vararg meals: Meal,
    )
}
