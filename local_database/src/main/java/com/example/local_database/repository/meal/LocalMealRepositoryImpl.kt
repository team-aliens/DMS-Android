package com.example.local_database.repository.meal

import com.example.local_database.datasource.declaration.LocalMealDataSource
import com.example.local_database.entity.meal.toEntity
import com.example.local_domain.entity.meal.MealEntity
import com.example.local_domain.repository.meal.LocalMealRepository
import javax.inject.Inject

class LocalMealRepositoryImpl @Inject constructor(
    private val localMealDataSource: LocalMealDataSource
) : LocalMealRepository {

    override suspend fun fetchMealList(): MealEntity =
        localMealDataSource.fetchMealList().toEntity()
}
