package team.aliens.dms.android.data.meal.mapper

import team.aliens.dms.android.core.database.entity.MealEntity
import team.aliens.dms.android.data.meal.model.Meal

internal fun MealEntity.toModel() = Meal(
    date = this.date,
    breakfast = this.breakfast,
    lunch = this.lunch,
    dinner = this.dinner,
)

internal fun Meal.toEntity() = MealEntity(
    date = this.date,
    breakfast = this.breakfast,
    lunch = this.lunch,
    dinner = this.dinner,
)

internal fun List<Meal>.toEntity() = this.map(Meal::toEntity)
