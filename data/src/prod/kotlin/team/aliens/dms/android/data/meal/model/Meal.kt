package team.aliens.dms.android.data.meal.model

import java.time.LocalDate
import team.aliens.dms.android.shared.date.util.today

data class Meal(
    val date: LocalDate = today,
    val breakfast: List<String> = emptyList(),
    val kcalOfBreakfast: String? = null,
    val lunch: List<String> = emptyList(),
    val kcalOfLunch: String? = null,
    val dinner: List<String> = emptyList(),
    val kcalOfDinner: String? = null,
)
