package team.aliens.data._datasource.local

import team.aliens.domain._model.meal.FetchMealInput
import team.aliens.domain._model.meal.Meal

interface LocalMealDataSource {

    suspend fun fetchMeal(
        input: FetchMealInput,
    ): Meal

    suspend fun saveMeal(
        meal: Meal,
    )

    suspend fun saveMeals(
        vararg meals: Meal,
    )
}
