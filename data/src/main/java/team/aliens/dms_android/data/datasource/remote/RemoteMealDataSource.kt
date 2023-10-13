package team.aliens.dms_android.data.datasource.remote

import team.aliens.dms.android.domain.model.meal.FetchMealsOutput
import team.aliens.dms.android.domain.model.meal.FetchMealsInput

interface RemoteMealDataSource {

    suspend fun fetchMeals(
        input: FetchMealsInput,
    ): FetchMealsOutput
}
