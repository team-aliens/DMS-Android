package team.aliens.network.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import team.aliens.network.annotation.RequiresAccessToken
import team.aliens.network.common.HttpPath.Meal.FetchMeals
import team.aliens.network.common.HttpProperty.PathVariable.Date
import team.aliens.network.model.meal.FetchMealsResponse

interface MealApiService {

    @GET(FetchMeals)
    @RequiresAccessToken
    suspend fun fetchMeals(
        @Path(Date) date: String,
    ): FetchMealsResponse
}
