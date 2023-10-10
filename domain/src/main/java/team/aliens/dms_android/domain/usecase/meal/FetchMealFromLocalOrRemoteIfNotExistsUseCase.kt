package team.aliens.dms_android.domain.usecase.meal

import team.aliens.dms_android.domain.model.meal.FetchMealInput
import team.aliens.dms_android.domain.model.meal.Meal
import team.aliens.dms_android.domain.repository.MealRepository
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