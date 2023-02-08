package team.aliens.data.remote.datasource.implementation

import com.example.data.remote.api.MealApi
import com.example.data.remote.datasource.declaration.RemoteMealDataSource
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealDataSourceImpl @Inject constructor(
    private val mealApi: MealApi,
) : RemoteMealDataSource {

    override suspend fun getMealValue(
        date: LocalDate
    ) = mealApi.getCafeteriaValue(date.toString())
}