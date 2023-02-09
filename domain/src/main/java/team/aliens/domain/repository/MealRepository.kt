package team.aliens.domain.repository

import team.aliens.domain.entity.MealEntity
import java.time.LocalDate

interface MealRepository {

    suspend fun fetchMealValue(date: LocalDate): MealEntity
}