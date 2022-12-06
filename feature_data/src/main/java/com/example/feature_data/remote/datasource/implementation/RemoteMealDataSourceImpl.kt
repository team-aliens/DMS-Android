package com.example.feature_data.remote.datasource.implementation

import com.example.feature_data.remote.api.MealApi
import com.example.feature_data.remote.datasource.declaration.RemoteMealDataSource
import com.example.feature_data.remote.response.meal.MealResponse
import com.example.feature_data.util.HttpHandler
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