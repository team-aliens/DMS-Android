package team.aliens.dms_android.network.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import team.aliens.dms_android.network.annotation.RequiresAccessToken
import team.aliens.dms_android.network.common.HttpPath.Meal.FetchMeals
import team.aliens.dms_android.network.common.HttpProperty.PathVariable.Date
import team.aliens.dms_android.network.model.meal.FetchMealsResponse

interface MealApiService {

    @GET(FetchMeals)
    @RequiresAccessToken
    suspend fun fetchMeals(
        @Path(Date) date: String,
    ): FetchMealsResponse
}