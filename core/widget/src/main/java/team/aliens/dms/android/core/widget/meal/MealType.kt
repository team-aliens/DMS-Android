package team.aliens.dms.android.core.widget.meal

import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.shared.date.util.now

private const val BreakfastStartTime: Int = 9
private const val LunchStartTime: Int = 13
private const val DinnerStartTime: Int = 19

enum class MealType(
    val icon: Int,
    val title: String,
) {
    Breakfast(
        icon = DmsIcon.BlueBreakfast,
        title = "아침",
    ),
    Launch(
        icon = DmsIcon.BlueLaunch,
        title = "점심",
    ),
    Dinner(
        icon = DmsIcon.BlueDinner,
        title = "저녁",
    ),
    ;

    companion object {
        internal fun getCurrentMealType(): MealType = when (now.hour) {
            in BreakfastStartTime until LunchStartTime -> Launch
            in LunchStartTime until DinnerStartTime -> Dinner
            else -> Breakfast
        }
    }
}
