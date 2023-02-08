package team.aliens.data.repository

import android.util.Log
import com.example.data.remote.datasource.declaration.RemoteMealDataSource
import com.example.data.remote.response.meal.toEntity
import com.example.domain.entity.MealEntity
import com.example.domain.repository.MealRepository
import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.entity.meal.MealRoomEntity
import java.time.LocalDate
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteMealDataSource: RemoteMealDataSource,
    private val localMealDataSource: LocalMealDataSource,
): MealRepository {

    override suspend fun fetchMealValue(date: LocalDate): MealEntity {
        Log.d("meals", "Repository")
        val response = remoteMealDataSource.getMealValue(date).toEntity()
        localMealDataSource.setMeal(response.meals.map { it.toDbEntity() })
        return response
    }
}

fun MealEntity.MealsValue.toDbEntity() =
    MealRoomEntity(
        date = date,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
    )
