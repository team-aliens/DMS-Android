package team.aliens.dms.android.data.meal.repository

import java.time.LocalDate
import team.aliens.dms.android.data.meal.exception.CannotFindMealException
import team.aliens.dms.android.data.meal.mapper.toEntity
import team.aliens.dms.android.data.meal.mapper.toModel
import team.aliens.dms.android.data.meal.model.Meal
import team.aliens.dms.android.database.meal.datasource.DatabaseMealDataSource
import team.aliens.dms.android.network.meal.datasource.NetworkMealDataSource
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import javax.inject.Inject

internal class MealRepositoryImpl @Inject constructor(
    private val databaseMealDataSource: DatabaseMealDataSource,
    private val networkMealDataSource: NetworkMealDataSource,
) : MealRepository() {
    override suspend fun fetchMeal(date: LocalDate): Result<Meal> {
        val refreshedMeal = updateMeal(date)
        val refreshFailure = refreshedMeal.exceptionOrNull()

        if (refreshFailure == null || refreshFailure is CannotFindMealException) {
            return refreshedMeal
        }

        return runCatchingCancellable {
            databaseMealDataSource.queryMeal(date).toModel()
                .takeIf { it.hasMenu() }
                ?: throw refreshFailure
        }.fold(
            onSuccess = Result.Companion::success,
            onFailure = { Result.failure(refreshFailure) },
        )
    }

    override suspend fun updateMeal(date: LocalDate): Result<Meal> =
        networkMealDataSource.fetchMeals(date).fold(
            onSuccess = { response ->
                runCatchingCancellable {
                    val meals = response.toModel()
                    databaseMealDataSource.saveMeals(meals.filter { it.hasMenu() }.toEntity())

                    meals.find { it.date == date } ?: throw CannotFindMealException()
                }
            },
            onFailure = { Result.failure(it) },
        )
}

private fun Meal.hasMenu(): Boolean =
    breakfast.isNotEmpty() || lunch.isNotEmpty() || dinner.isNotEmpty()
