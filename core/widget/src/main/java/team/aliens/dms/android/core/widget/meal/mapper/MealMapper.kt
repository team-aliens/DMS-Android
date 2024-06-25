package team.aliens.dms.android.core.widget.meal.mapper

import team.aliens.dms.android.core.widget.meal.MealInfo
import team.aliens.dms.android.data.meal.model.Meal

internal fun Meal.toEntity() = MealInfo.Available(
    date = this.date.toString(),
    breakfast = this.breakfast,
    kcalOfBreakfast = this.kcalOfBreakfast!!,
    lunch = this.lunch,
    kcalOfLunch = this.kcalOfLunch!!,
    dinner = this.dinner,
    kcalOfDinner = this.kcalOfDinner!!,
)
