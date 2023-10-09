package team.aliens.dms_android.domain.repository

import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput
import team.aliens.domain.model.meal.Meal

interface MealRepository {

    @Deprecated("does not save into local db")
    suspend fun fetchMeal(
        input: FetchMealInput,
    ): Meal

    @Deprecated("does not save into local db")
    suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput

    suspend fun saveMeal(
        meal: Meal,
    )

    suspend fun saveMeals(
        vararg meals: Meal,
    )

    suspend fun fetchMealFromLocal(input: FetchMealInput): Meal

    // suspend fun fetchMealFromRemote(input: FetchMealFromRemote): Meal

    suspend fun fetchMealFromRemoteAndSave(input: FetchMealInput): Meal

    suspend fun fetchMealFromLocalOrRemoteIfNotExists(input: FetchMealInput): Meal

    suspend fun fetchMealsFromRemote(input: FetchMealsInput): FetchMealsOutput

    suspend fun fetchMealFromRemoteAndSave(input: FetchMealsInput): FetchMealsOutput
}
