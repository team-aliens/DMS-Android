package team.aliens.dms_android.widget.meal

import team.aliens.design_system.icon.DormIcon
import java.time.LocalDateTime

/**
 * 급식 타입을 정의합니다.
 *
 * [Breakfast] 아침
 * [Lunch] 점심
 * [Dinner] 저녁
 */
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
            return when (localDateTime.hour) {
                in MorningStartTime until LunchStartTime -> Breakfast
                in LunchStartTime until DinnerStartTime -> Lunch
                else -> Breakfast
            }
        }

        private const val MorningStartTime: Int = 0
        private const val LunchStartTime: Int = 8
        private const val DinnerStartTime: Int = 13
    }
}