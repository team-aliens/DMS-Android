package team.aliens.domain.repository

import com.example.domain.entity.MealEntity
import java.time.LocalDate

interface MealRepository {

    suspend fun fetchMealValue(date: LocalDate): MealEntity
}