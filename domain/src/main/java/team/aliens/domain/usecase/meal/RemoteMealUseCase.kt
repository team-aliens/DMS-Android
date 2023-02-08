package team.aliens.domain.usecase.meal

import android.util.Log
import com.example.domain.entity.MealEntity
import com.example.domain.repository.MealRepository
import com.example.domain.usecase.UseCase
import java.time.LocalDate
import javax.inject.Inject

class RemoteMealUseCase @Inject constructor(
    private val mealRepository: MealRepository
) : UseCase<LocalDate, MealEntity>() {
    override suspend fun execute(data: LocalDate): MealEntity {
        Log.d("meals", "UseCase")
        return mealRepository.fetchMealValue(data)
    }
}
