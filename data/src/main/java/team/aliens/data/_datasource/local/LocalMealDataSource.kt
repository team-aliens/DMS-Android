package team.aliens.data._datasource.local

import team.aliens.domain._model.meal.Meal

interface LocalMealDataSource {

    suspend fun fetchMeal(
        date: String,
    ): Meal

    suspend fun saveMeal(
        meals: Meal,
    )

    suspend fun saveMeals(
        vararg meals: Meal,
    )
}
