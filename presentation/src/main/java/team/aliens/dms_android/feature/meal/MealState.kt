package team.aliens.dms_android.feature.meal

import kotlinx.coroutines.flow.MutableStateFlow
import team.aliens.dms_android.base.MviState
import java.time.LocalDate

data class MealState(
    var meals: MutableStateFlow<FormedMeal> = MutableStateFlow(FormedMeal()),
    var selectedDay: LocalDate = LocalDate.now(),
    var hasNewNotice: Boolean = false,
) : MviState {
    companion object {
        fun getDefaultInstance() = MealState()
    }
}

// TODO : need to be renamed
data class FormedMeal(
    var breakfast: Pair<List<String>, String> = listOf("") to "",
    var lunch: Pair<List<String>, String> = listOf("") to "",
    var dinner: Pair<List<String>, String> = listOf("") to "",
)
