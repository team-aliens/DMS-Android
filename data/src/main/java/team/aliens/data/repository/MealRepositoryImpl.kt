package team.aliens.data.repository

import team.aliens.data.datasource.local.LocalMealDataSource
import team.aliens.data.datasource.remote.RemoteMealDataSource
import team.aliens.domain.model.meal.FetchMealInput
import team.aliens.domain.model.meal.FetchMealsInput
import team.aliens.domain.model.meal.FetchMealsOutput
import team.aliens.domain.model.meal.Meal
import team.aliens.domain.model.meal.toTypedArray
import team.aliens.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val localMealDataSource: LocalMealDataSource,
    private val remoteMealDataSource: RemoteMealDataSource,
) : MealRepository {

    @Deprecated("does not save into local db")
    override suspend fun fetchMeal(
        input: FetchMealInput,
    ): Meal {
        try {
            return localMealDataSource.fetchMeal(
                input = input,
            )
        } catch (e: Exception) {
            val fetchMealsInput = FetchMealsInput(
                date = input.date,
            )

            this.fetchMeals(
                input = fetchMealsInput,
            )
        }

        return localMealDataSource.fetchMeal(
            input = input,
        )
    }

    @Deprecated("does not save into local db")
    override suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput {
        return remoteMealDataSource.fetchMeals(
            input = input,
        ).also {
            this.saveMeals(
                meals = it.meals.toTypedArray(),
            )
        }
    }

    override suspend fun saveMeal(
        meal: Meal,
    ) {
        localMealDataSource.saveMeal(
            meal = meal,
        )
    }

    override suspend fun saveMeals(
        vararg meals: Meal,
    ) {
        localMealDataSource.saveMeals(
            meals = meals,
        )
    }
}
