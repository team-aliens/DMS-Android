package team.aliens.dms_android.data.repository

import team.aliens.dms_android.data.datasource.local.LocalMealDataSource
import team.aliens.dms_android.data.datasource.remote.RemoteMealDataSource
import team.aliens.dms.android.domain.model.meal.FetchMealInput
import team.aliens.dms.android.domain.model.meal.FetchMealsInput
import team.aliens.dms.android.domain.model.meal.FetchMealsOutput
import team.aliens.dms.android.domain.model.meal.Meal
import team.aliens.dms.android.domain.model.meal.toModel
import team.aliens.dms.android.domain.model.meal.toTypedArray
import team.aliens.dms.android.domain.repository.MealRepository
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

    override suspend fun fetchMealFromLocal(
        input: FetchMealInput,
    ): Meal {
        return localMealDataSource.fetchMeal(input)
    }

    // todo review later
    override suspend fun fetchMealFromRemoteAndSave(
        input: FetchMealInput,
    ): Meal {
        val fetchedMeals = this.fetchMealsFromRemote(
            FetchMealsInput(
                date = input.date,
            )
        ).meals.toModel()
        this.saveMeals(*fetchedMeals.toTypedArray())
        return fetchedMeals.first { it.date == input.date }
    }

    override suspend fun fetchMealFromLocalOrRemoteIfNotExists(
        input: FetchMealInput,
    ): Meal {
        return kotlin.runCatching {
            this.fetchMealFromLocal(input)
        }.getOrNull() ?: fetchMealFromRemoteAndSave(input)
    }

    override suspend fun fetchMealsFromRemote(
        input: FetchMealsInput,
    ): FetchMealsOutput {
        return remoteMealDataSource.fetchMeals(input = input)
    }

    override suspend fun fetchMealFromRemoteAndSave(
        input: FetchMealsInput,
    ): FetchMealsOutput {
        return this.fetchMealsFromRemote(input).also {
            this.saveMeals(*it.meals.toTypedArray())
        }
    }
}
