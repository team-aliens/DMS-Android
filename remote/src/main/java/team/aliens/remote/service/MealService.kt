package team.aliens.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Meals.FetchMeals
import team.aliens.remote.common.HttpProperty.PathVariable.Date
import team.aliens.remote.model.meal.FetchMealsResponse

interface MealService {

    @GET(FetchMeals)
    @RequiresAccessToken
    suspend fun fetchMeals(
        @Path(Date) date: String,
    ): FetchMealsResponse
}
