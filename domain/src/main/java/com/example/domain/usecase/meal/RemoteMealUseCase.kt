package com.example.domain.usecase.meal

import com.example.domain.entity.MealEntity
import com.example.domain.repository.MealRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) : UseCase<LocalDate, Flow<MealEntity>>() {
    override suspend fun execute(data: LocalDate) =
        mealRepository.fetchMealValue(data)
}
