package team.aliens.data.remote.api

import com.example.data.remote.response.meal.MealResponse
import com.example.data.remote.url.DmsUrl
import retrofit2.http.GET
import retrofit2.http.Path

interface MealApi {

    @GET(DmsUrl.meal)
    suspend fun getCafeteriaValue(
        @Path("date") date: String,
    ): MealResponse
}
