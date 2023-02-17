package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteMealDataSource
import team.aliens.data.remote.response.meal.toEntity
import team.aliens.domain.entity.MealEntity
import team.aliens.domain.repository.MealRepository
import team.aliens.local_database.datasource.declaration.LocalMealDataSource
import team.aliens.local_database.entity.meal.MealRoomEntity
import java.time.LocalDate
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteMealDataSource: RemoteMealDataSource,
    private val localMealDataSource: LocalMealDataSource,
) : MealRepository {

    override suspend fun fetchMealValue(date: LocalDate): MealEntity {
        val response = remoteMealDataSource.getMealValue(date).toEntity()
        localMealDataSource.setMeal(response.meals.map { it.toDbEntity() })
        return response
    }
}

fun MealEntity.MealsValue.toDbEntity() = MealRoomEntity(
    date = date,
    breakfast = breakfast,
    lunch = lunch,
    dinner = dinner,
)
