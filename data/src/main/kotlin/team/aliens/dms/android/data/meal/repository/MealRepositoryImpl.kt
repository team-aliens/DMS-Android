package team.aliens.dms.android.data.meal.repository

import java.time.LocalDate
import team.aliens.dms.android.data.meal.exception.CannotFindMealException
import team.aliens.dms.android.data.meal.exception.CannotSaveMealException
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
        val cachedMeal = runCatchingCancellable { databaseMealDataSource.queryMeal(date).toModel() }
        if (cachedMeal.isSuccess) {
            return cachedMeal
        }

        return updateMeal(date).fold(
            onSuccess = { Result.success(it) },
            onFailure = { exception ->
                when (exception) {
                    is CannotSaveMealException -> Result.failure(exception)
                    else -> Result.failure(CannotSaveMealException())
                }
            },
        )
    }

    override suspend fun updateMeal(date: LocalDate): Result<Meal> =
        networkMealDataSource.fetchMeals(date).fold(
            onSuccess = { response ->
                runCatchingCancellable {
                    val meals = response.toModel()
                    databaseMealDataSource.saveMeals(meals.toEntity())

                    meals.find { it.date == date } ?: throw CannotFindMealException()
                }
            },
            onFailure = { Result.failure(it) },
        )
}
