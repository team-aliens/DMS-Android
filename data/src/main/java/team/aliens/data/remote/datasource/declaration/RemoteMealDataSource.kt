package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.response.meal.MealResponse
import java.time.LocalDate

interface RemoteMealDataSource {

    suspend fun getMealValue(date: LocalDate): MealResponse
}