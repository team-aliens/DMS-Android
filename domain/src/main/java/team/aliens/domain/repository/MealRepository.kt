package team.aliens.domain.repository

import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput
import team.aliens.domain.model.meal.Meal

interface MealRepository {

    suspend fun fetchMeal(
        input: FetchMealInput,
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
