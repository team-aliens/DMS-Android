package com.example.local_domain.usecase.meal

import com.example.local_domain.entity.meal.MealEntity
import com.example.local_domain.repository.meal.LocalMealRepository
import com.example.local_domain.usecase.UseCase
import javax.inject.Inject

class LocalMealUseCase @Inject constructor(
    private val localMealRepository: LocalMealRepository
): UseCase<Unit, MealEntity>() {

    override suspend fun execute(data: Unit): MealEntity =
        localMealRepository.fetchMealList()
}
