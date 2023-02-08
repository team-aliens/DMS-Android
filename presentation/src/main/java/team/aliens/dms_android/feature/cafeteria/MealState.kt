package team.aliens.dms_android.feature.cafeteria

import team.aliens.dms_android.base.MviState
import java.time.LocalDate

data class MealState(
    val meal: String,
    var mealList: MealList = MealList(),
    var today: LocalDate = LocalDate.now(),
    var a: Int = 0,
    var b: Int = 0,
    var noticeBoolean: Boolean = false,
) : MviState {
    companion object {
        fun initial() = MealState(
            meal = "",
        )
    }
}

data class MealList(
    var breakfast: List<String> = listOf(""),
    var lunch: List<String> = listOf(""),
    var dinner: List<String> = listOf(""),
)
