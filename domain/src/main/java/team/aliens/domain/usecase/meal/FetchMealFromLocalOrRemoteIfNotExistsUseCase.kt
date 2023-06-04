package team.aliens.domain.usecase.meal

import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.Meal
import team.aliens.domain.repository.MealRepository
import javax.inject.Inject

class FetchMealFromLocalOrRemoteIfNotExistsUseCase @Inject constructor(
    private val mealRepository: MealRepository,
) {
    suspend operator fun invoke(
        fetchMealInput: FetchMealInput,
    ): Meal {
        return mealRepository.fetchMealFromLocalOrRemoteIfNotExists(input = fetchMealInput)
    }
}