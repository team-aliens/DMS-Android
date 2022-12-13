package com.example.feature_domain.usecase.meal

import com.example.feature_domain.repository.MealRepository
import com.example.feature_domain.usecase.UseCase
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
): UseCase<LocalDate, Unit>() {
    override suspend fun execute(data: LocalDate) {
        mealRepository.fetchMealValue(data)
    }
}
