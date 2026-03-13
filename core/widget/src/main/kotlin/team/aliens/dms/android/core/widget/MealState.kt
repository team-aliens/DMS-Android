package team.aliens.dms.android.core.widget.meal

data class MealState(
    val mealType: MealType = MealType.Breakfast,
    val meal: List<String> = emptyList(),
    val calories: String = "",
)
