package team.aliens.dms.android.core.widget.meal

import kotlinx.serialization.Serializable

@Serializable
sealed interface MealInfo {

    @Serializable
    data object Loading : MealInfo

    @Serializable
    data class Available(
        val date: String,
        val breakfast: List<String>,
        val kcalOfBreakfast: String,
        val lunch: List<String>,
        val kcalOfLunch: String,
        val dinner: List<String>,
        val kcalOfDinner: String,
    ) : MealInfo

    @Serializable
    data object Unavailable : MealInfo
}
