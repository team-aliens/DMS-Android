package team.aliens.dms.android.network.meal.model

import com.google.gson.annotations.SerializedName

data class FetchMealsResponse(
    @SerializedName("meals") val meals: List<MealResponse>,
) {
    data class MealResponse(
        @SerializedName("date") val date: String,
        @SerializedName("breakfast") val breakfast: List<String>,
        @SerializedName("lunch") val lunch: List<String>,
        @SerializedName("dinner") val dinner: List<String>,
    )
}
