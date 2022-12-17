package com.example.data.repository

import com.example.data.remote.datasource.declaration.RemoteMealDataSource
import com.example.data.remote.response.meal.toEntity
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.MealEntity
import com.example.domain.repository.MealRepository
import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.entity.meal.MealRoomEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteMealDataSource: RemoteMealDataSource,
    private val localMealDataSource: LocalMealDataSource,
): MealRepository {

    override suspend fun fetchMealValue(date: LocalDate): Flow<MealEntity> =
        OfflineCacheUtil<MealEntity>()
            .remoteData { remoteMealDataSource.getMealValue(date).toEntity() }
            .doOnNeedRefresh { localMealDataSource.setMeal(it.meals.map { it.toDbEntity() }) }
            .createFlow()
}

fun MealEntity.MealsValue.toDbEntity() =
    MealRoomEntity(
        date = date,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
    )
