package team.aliens.dms_android.feature.cafeteria

import team.aliens.dms_android.base.MviState
import java.time.LocalDate

data class MealState(
    val meal: String,
    var mealList: MealList = MealList(),
    var selectedDay: LocalDate = LocalDate.now(),
    var hasNewNotice: Boolean = false,
) : MviState {
    companion object {
        fun getDefaultInstance() = MealState(
            meal = "",
        )
    }
}

data class MealList(
    var breakfast: Pair<List<String>, String> = listOf("") to "",
    var lunch: Pair<List<String>, String> = listOf("") to "",
    var dinner: Pair<List<String>, String> = listOf("") to "",
)
