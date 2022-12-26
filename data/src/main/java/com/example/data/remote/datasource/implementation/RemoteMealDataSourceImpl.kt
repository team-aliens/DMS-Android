package com.example.data.remote.datasource.implementation

import com.example.data.remote.api.MealApi
import com.example.data.remote.datasource.declaration.RemoteMealDataSource
import com.example.data.remote.response.meal.MealResponse
import com.example.data.util.HttpHandler
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealDataSourceImpl @Inject constructor(
    private val mealApi: MealApi,
): RemoteMealDataSource {

    override suspend fun getMealValue(
        date: LocalDate
    ) = HttpHandler<MealResponse>()
        .httpRequest { mealApi.getCafeteriaValue(date) }
        .sendRequest()
}