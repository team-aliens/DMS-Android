package team.aliens.dms.android.data._legacy.datasource.local

import team.aliens.dms.android.domain.model.meal.FetchMealInput
import team.aliens.dms.android.domain.model.meal.Meal

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
