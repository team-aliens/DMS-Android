package team.aliens.dms.android.data.meal.mapper

import team.aliens.dms.android.core.database.entity.MealEntity
import team.aliens.dms.android.data.meal.model.Meal

internal fun MealEntity.toModel() = Meal(
    date = this.date,
    breakfast = this.breakfast,
    kcalOfBreakfast = this.kcalOfBreakfast,
    lunch = this.lunch,
    kcalOfLunch = this.kcalOfLunch,
    dinner = this.dinner,
    kcalOfDinner = this.kcalOfDinner,
)

internal fun Meal.toEntity() = MealEntity(
    date = this.date,
    breakfast = this.breakfast,
    kcalOfBreakfast = this.kcalOfBreakfast,
    lunch = this.lunch,
    kcalOfLunch = this.kcalOfLunch,
    dinner = this.dinner,
    kcalOfDinner = this.kcalOfDinner,
)

internal fun List<Meal>.toEntity() = this.map(Meal::toEntity)
