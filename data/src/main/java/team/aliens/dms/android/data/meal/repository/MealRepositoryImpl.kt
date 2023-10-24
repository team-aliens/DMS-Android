package team.aliens.dms.android.data.meal.repository

import org.threeten.bp.LocalDate
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
    override suspend fun fetchMeal(date: LocalDate): Meal = try {
        databaseMealDataSource.queryMeal(date).toModel()
    } catch (_: Exception) {
        try {
            networkMealDataSource.fetchMeals(date).toModel().also { meals ->
                databaseMealDataSource.saveMeals(meals.toEntity())
            }
                .find { it.date == date }!!
        } catch (_: Exception) {
            throw CannotFindMealException()
        }
    }
}
