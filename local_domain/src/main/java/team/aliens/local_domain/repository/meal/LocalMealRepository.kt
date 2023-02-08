package team.aliens.local_domain.repository.meal

import team.aliens.local_domain.entity.meal.MealEntity

interface LocalMealRepository {

    suspend fun fetchMealList(date: String): MealEntity
}
