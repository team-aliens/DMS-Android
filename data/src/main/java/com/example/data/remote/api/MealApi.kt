package com.example.data.remote.api

import com.example.data.remote.response.meal.MealResponse
import com.example.data.remote.url.DmsUrl
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate

interface MealApi {

    @GET(DmsUrl.meal)
    suspend fun getCafeteriaValue(
        @Path("date") date: String,
    ): MealResponse
}
