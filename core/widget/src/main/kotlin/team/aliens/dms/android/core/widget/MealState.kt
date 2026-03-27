package team.aliens.dms.android.core.widget

import androidx.compose.runtime.Immutable

@Immutable
data class MealState(
    val mealType: MealType = MealType.Breakfast,
    val meal: List<String> = emptyList(),
    val calories: String = "",
)
