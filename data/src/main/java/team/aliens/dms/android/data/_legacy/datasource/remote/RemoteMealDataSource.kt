package team.aliens.dms.android.data._legacy.datasource.remote

import team.aliens.dms.android.domain.model.meal.FetchMealsOutput
import team.aliens.dms.android.domain.model.meal.FetchMealsInput

interface RemoteMealDataSource {

    suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput
}
