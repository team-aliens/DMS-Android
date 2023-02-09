package team.aliens.data.remote.response.meal

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.MealEntity

data class MealResponse(
    @SerializedName("meals") val meals: List<Meals>,
) {
    data class Meals(
        @SerializedName("date") val date: String,
        @SerializedName("breakfast") val breakfast: List<String>,
        @SerializedName("lunch") val lunch: List<String>,
        @SerializedName("dinner") val dinner: List<String>,
    )
}

fun MealResponse.Meals.toEntity() = MealEntity.MealsValue(
    date = date,
    breakfast = breakfast,
    lunch = lunch,
    dinner = dinner,
)

fun MealResponse.toEntity() = MealEntity(meals = meals.map { it.toEntity() })
