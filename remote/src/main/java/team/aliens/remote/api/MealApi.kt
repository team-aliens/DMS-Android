package team.aliens.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import team.aliens.remote.common.DormHttpPath.Meals.FetchMeals
import team.aliens.remote.common.HttpProperty.PathVariable.Date
import team.aliens.remote.model.meal.FetchMealsResponse

interface MealApi {

    @GET(FetchMeals)
    suspend fun fetchMeals(
        @Path(Date) date: String,
    ): FetchMealsResponse
}
