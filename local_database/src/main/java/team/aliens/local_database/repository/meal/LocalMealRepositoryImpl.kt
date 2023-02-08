package team.aliens.local_database.repository.meal

import team.aliens.local_database.datasource.declaration.LocalMealDataSource
import team.aliens.local_database.entity.meal.toEntity
import team.aliens.local_domain.entity.meal.MealEntity
import team.aliens.local_domain.repository.meal.LocalMealRepository
import javax.inject.Inject

class LocalMealRepositoryImpl @Inject constructor(
    private val localMealDataSource: LocalMealDataSource,
) : LocalMealRepository {

    override suspend fun fetchMealList(date: String): MealEntity =
        localMealDataSource.fetchMealList(date).toEntity()
}
