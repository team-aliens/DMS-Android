package team.aliens.dms.android.data.meal.repository

import java.time.LocalDate
import team.aliens.dms.android.data.meal.exception.CannotFindMealException
import team.aliens.dms.android.data.meal.mapper.toEntity
import team.aliens.dms.android.data.meal.mapper.toModel
import team.aliens.dms.android.data.meal.model.Meal
import team.aliens.dms.android.database.meal.datasource.DatabaseMealDataSource
import team.aliens.dms.android.network.meal.datasource.NetworkMealDataSource
import javax.inject.Inject

internal class MealRepositoryImpl @Inject constructor(
    private val databaseMealDataSource: DatabaseMealDataSource,
    private val networkMealDataSource: NetworkMealDataSource,
) : MealRepository() {
    override suspend fun fetchMeal(date: LocalDate): Result<Meal> = runCatching {
        databaseMealDataSource.queryMeal(date).toModel()
    }.recoverCatching {
        updateMeal(date = date).getOrElse { throw CannotFindMealException() }
    }

    override suspend fun updateMeal(date: LocalDate): Result<Meal> =
        networkMealDataSource.fetchMeals(date).map { response ->
            response.toModel().also { meals ->
                databaseMealDataSource.saveMeals(meals.toEntity())
            }.find { it.date == date } ?: throw CannotFindMealException()
        }
}
