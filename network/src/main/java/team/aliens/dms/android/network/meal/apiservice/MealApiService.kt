package team.aliens.dms.android.network.meal.apiservice

import org.threeten.bp.LocalDate
import retrofit2.http.GET
import retrofit2.http.Path
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.meal.model.FetchMealsResponse

internal interface MealApiService {

    @GET("/meals/{date}")
    @RequiresAccessToken
    suspend fun fetchMeals(@Path("date") date: LocalDate): FetchMealsResponse
}
