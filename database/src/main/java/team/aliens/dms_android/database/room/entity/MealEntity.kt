package team.aliens.dms_android.database.room.entity

import team.aliens.dms_android.core.database.entity.MealEntity
import team.aliens.dms.android.domain.model.meal.Meal

internal fun MealEntity.toDomain(): Meal {
    return Meal(
        date = this.date,
        breakfast = this.breakfast,
        lunch = this.lunch,
        dinner = this.dinner,
    )
}

internal fun Meal.toData(): MealEntity {
    return MealEntity(
        date = this.date,
        breakfast = this.breakfast,
        lunch = this.lunch,
        dinner = this.dinner,
    )
}

internal fun Array<Meal>.toData(): Array<MealEntity> {
    return this.map(Meal::toData).toTypedArray()
}
