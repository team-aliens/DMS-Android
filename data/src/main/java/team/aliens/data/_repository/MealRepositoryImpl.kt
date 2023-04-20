package team.aliens.data._repository

import team.aliens.data._datasource.local.LocalMealDataSource
import team.aliens.data._datasource.remote.RemoteMealDataSource
import team.aliens.domain._model.meal.FetchMealsInput
import team.aliens.domain._model.meal.FetchMealsOutput
import team.aliens.domain._model.meal.Meal
import team.aliens.domain._model.meal.toTypedArray
import team.aliens.domain._repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val localMealDataSource: LocalMealDataSource,
    private val remoteMealDataSource: RemoteMealDataSource,
) : MealRepository {

    override suspend fun fetchMeal(
        date: String,
    ): Meal {

        try {
            return localMealDataSource.fetchMeal(
                date = date,
            )
        } catch (e: Exception) {
            this.fetchMeals(
                FetchMealsInput(
                    date = date,
                ),
            )
        }

        return localMealDataSource.fetchMeal(
            date = date,
        )
    }

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
