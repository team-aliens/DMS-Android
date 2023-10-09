package team.aliens.dms_android.domain.usecase.meal

import team.aliens.dms_android.domain.model.meal.FetchMealsInput
import team.aliens.dms_android.domain.model.meal.FetchMealsOutput
import team.aliens.dms_android.domain.repository.MealRepository
import javax.inject.Inject

class FetchMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository,
) {
    suspend operator fun invoke(
        fetchMealsInput: FetchMealsInput,
    ): FetchMealsOutput {
        return mealRepository.fetchMeals(
            input = fetchMealsInput,
        )
    }
}
