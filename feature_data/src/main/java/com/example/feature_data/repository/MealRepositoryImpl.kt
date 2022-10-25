package com.example.feature_data.repository

import com.example.feature_data.remote.datasource.declaration.RemoteMealDataSource
import com.example.feature_data.remote.response.meal.toEntity
import com.example.feature_data.util.OfflineCacheUtil
import com.example.feature_domain.entity.MealEntity
import com.example.feature_domain.repository.MealRepository
import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.entity.meal.MealRoomEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteMealDataSource: RemoteMealDataSource,
    private val localMealDataSource: LocalMealDataSource,
): MealRepository {

    override suspend fun fetchMealValue(date: String): Flow<MealEntity> =
        OfflineCacheUtil<MealEntity>()
            .remoteData { remoteMealDataSource.getMealValue(date).toEntity() }
            .doOnNeedRefresh { localMealDataSource.setMeal(it.toDbEntity()) }
            .createFlow()
}

fun MealEntity.MealsValue.toDbEntity() =
    MealRoomEntity.MealsRoomValue(
        date = date,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
    )

fun MealEntity.toDbEntity() =
    MealRoomEntity(
        meals = meals.map { it.toDbEntity() }
    )
