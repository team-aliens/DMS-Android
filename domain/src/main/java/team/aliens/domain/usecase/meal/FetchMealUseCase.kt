package team.aliens.domain.usecase.meal

import team.aliens.domain._model.meal.Meal
import team.aliens.domain._repository.MealRepository
import javax.inject.Inject

class FetchMealUseCase @Inject constructor(
    private val mealRepository: MealRepository,
) {
    suspend operator fun invoke(
        date: String,
    ): Meal {
        return mealRepository.fetchMeal(
            date = date,
        )
    }
}
