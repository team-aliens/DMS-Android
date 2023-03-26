package team.aliens.remote.model.meal

import com.google.gson.annotations.SerializedName

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
