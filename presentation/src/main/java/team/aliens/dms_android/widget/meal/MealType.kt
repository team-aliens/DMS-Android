package team.aliens.dms_android.widget.meal

import team.aliens.design_system.icon.DormIcon
import java.time.LocalDateTime

enum class MealType(
    val icon: DormIcon,
    val title: String,
) {
    Breakfast(
        icon = DormIcon.BlueBreakfast,
        title = "아침",
    ),
    Lunch(
        icon = DormIcon.BlueLunch,
        title = "점심",
    ),
    Dinner(
        icon = DormIcon.BlueDinner,
        title = "저녁",
    ),
    ;

    companion object {
        fun getCurrentMealType(localDateTime: LocalDateTime): MealType {
            val currentHour = localDateTime.hour

            val morningStart = 0
            val lunchStart = 8
            val eveningStart = 13

            return when (currentHour) {
                in morningStart until lunchStart -> Breakfast
                in lunchStart until eveningStart -> Lunch
                else -> Breakfast
            }
        }
    }
}