package com.example.feature_data.remote.api

import com.example.feature_data.remote.response.meal.MealResponse
import com.example.feature_data.remote.url.DmsUrl
import retrofit2.http.GET
import retrofit2.http.Path

interface MealApi {

    @GET (DmsUrl.meal)
    fun getCafeteriaValue(
        @Path("date") date: String,
    ): MealResponse
}