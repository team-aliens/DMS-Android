package com.example.feature_domain.usecase.meal

import com.example.feature_domain.repository.MealRepository
import com.example.feature_domain.usecase.UseCase
import javax.inject.Inject

class RemoteMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
): UseCase<String, Unit>() {
    override suspend fun execute(data: String) {
        mealRepository.fetchMealValue(data)
    }
}
