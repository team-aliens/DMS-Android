package team.aliens.dms.android.domain.model.meal

/**
 * A meal information entity contains meal's date, breakfasts, lunches, and dinners
 * @property date meal's date
 * @property breakfast breakfast dishes of date
 * @property lunch lunch dishes of date
 * @property dinner dinner dishes of date
 */
data class Meal(
    val date: String,
    val breakfast: List<String>,
    val lunch: List<String>,
    val dinner: List<String>,
)
