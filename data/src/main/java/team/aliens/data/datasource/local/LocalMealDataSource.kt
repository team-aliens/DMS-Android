package team.aliens.data.datasource.local

import team.aliens.dms_android.domain.model.meal.FetchMealInput
import team.aliens.dms_android.domain.model.meal.Meal

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
