package team.aliens.dms_android.network.model.meal

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.meal.FetchMealsOutput

data class FetchMealsResponse(
    @SerializedName("meals") val meals: List<Meal>,
) {
    data class Meal(
        @SerializedName("date") val date: String,
        @SerializedName("breakfast") val breakfast: List<String>,
        @SerializedName("lunch") val lunch: List<String>,
        @SerializedName("dinner") val dinner: List<String>,
    )
}

internal fun FetchMealsResponse.toDomain(): FetchMealsOutput {
    return FetchMealsOutput(
        meals = this.meals.toDomain(),
    )
}

internal fun FetchMealsResponse.Meal.toDomain(): FetchMealsOutput.MealInformation {
    return FetchMealsOutput.MealInformation(
        date = this.date,
        breakfast = this.breakfast,
        lunch = this.lunch,
        dinner = this.dinner,
    )
}

internal fun List<FetchMealsResponse.Meal>.toDomain(): List<FetchMealsOutput.MealInformation> {
    return this.map(
        FetchMealsResponse.Meal::toDomain,
    )
}
