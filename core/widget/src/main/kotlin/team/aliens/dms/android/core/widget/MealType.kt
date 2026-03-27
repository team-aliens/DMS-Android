package team.aliens.dms.android.core.widget

import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.shared.date.util.now

private const val BREAKFAST_START_TIME: Int = 9
private const val LUNCH_START_TIME: Int = 13
private const val DINNER_START_TIME: Int = 19

enum class MealType(
    val icon: Int,
    val title: String,
) {
    Breakfast(
        icon = DmsIcon.BlueBreakfast,
        title = "아침",
    ),
    Launch(
        icon = DmsIcon.BlueLunch,
        title = "점심",
    ),
    Dinner(
        icon = DmsIcon.BlueDinner,
        title = "저녁",
    ),
    ;

    companion object {
        internal fun getCurrentMealType(): MealType = when (now.hour) {
            in BREAKFAST_START_TIME until LUNCH_START_TIME -> Launch
            in LUNCH_START_TIME until DINNER_START_TIME -> Dinner
            else -> Breakfast
        }
    }
}
