package team.aliens.domain.usecase.meal

import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.domain._repository.MealRepository
import javax.inject.Inject

class FetchMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository,
) {
    suspend operator fun invoke(
        date: String,
    ): FetchMealsOutput {
        return mealRepository.fetchMeals(date)
    }
}
