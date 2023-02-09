package team.aliens.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import team.aliens.data.remote.response.meal.MealResponse
import team.aliens.data.remote.url.DmsUrl

interface MealApi {

    @GET(DmsUrl.meal)
    suspend fun getCafeteriaValue(
        @Path("date") date: String,
    ): MealResponse
}
