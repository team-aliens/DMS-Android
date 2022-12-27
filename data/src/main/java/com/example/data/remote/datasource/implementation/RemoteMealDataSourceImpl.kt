package com.example.data.remote.datasource.implementation

import android.util.Log
import com.example.data.remote.api.MealApi
import com.example.data.remote.datasource.declaration.RemoteMealDataSource
import com.example.data.remote.response.meal.MealResponse
import com.example.data.util.HttpHandler
import com.example.data.util.sendHttpRequest
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealDataSourceImpl @Inject constructor(
    private val mealApi: MealApi,
) : RemoteMealDataSource {

    override suspend fun getMealValue(
        date: LocalDate
    ) = mealApi.getCafeteriaValue(date.toString())
}