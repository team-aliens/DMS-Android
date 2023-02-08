package team.aliens.domain.usecase.meal

import android.util.Log
import team.aliens.domain.entity.MealEntity
import team.aliens.domain.repository.MealRepository
import team.aliens.domain.usecase.UseCase
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealUseCase @Inject constructor(
    private val mealRepository: MealRepository,
) : UseCase<LocalDate, MealEntity>() {
    override suspend fun execute(data: LocalDate): MealEntity {
        Log.d("meals", "UseCase")
        return mealRepository.fetchMealValue(data)
    }
}
