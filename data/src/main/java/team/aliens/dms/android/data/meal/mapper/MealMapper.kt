package team.aliens.dms.android.data.meal.mapper

import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.database.entity.MealEntity
import team.aliens.dms.android.domain.model.meal.Meal

internal fun MealEntity.toDomain() = Meal(
    date = this.date.toString(), // TODO: change type
    breakfast = this.breakfast,
    lunch = this.lunch,
    dinner = this.dinner,
)

internal fun Meal.toData() = MealEntity(
    date = LocalDate.now(), // TODO: this.date
    breakfast = this.breakfast,
    lunch = this.lunch,
    dinner = this.dinner,
)

internal fun List<Meal>.toData() = this.map(Meal::toData)
