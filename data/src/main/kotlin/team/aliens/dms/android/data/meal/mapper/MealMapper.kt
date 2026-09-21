package team.aliens.dms.android.data.meal.mapper

import team.aliens.dms.android.core.database.entity.MealEntity
import team.aliens.dms.android.data.meal.model.Meal
import team.aliens.dms.android.network.meal.model.FetchMealsResponse
import team.aliens.dms.android.shared.date.toLocalDate

internal fun MealEntity.toModel() = Meal(
    date = this.date,
    breakfast = this.breakfast,
    kcalOfBreakfast = this.kcalOfBreakfast,
    lunch = this.lunch,
    kcalOfLunch = this.kcalOfLunch,
    dinner = this.dinner,
    kcalOfDinner = this.kcalOfDinner,
)

internal fun List<Meal>.toEntity() = this.map(Meal::toEntity)

private fun Meal.toEntity() = MealEntity(
    date = this.date,
    breakfast = this.breakfast,
    kcalOfBreakfast = this.kcalOfBreakfast,
    lunch = this.lunch,
    kcalOfLunch = this.kcalOfLunch,
    dinner = this.dinner,
    kcalOfDinner = this.kcalOfDinner,
)

internal fun FetchMealsResponse.toModel(): List<Meal> = this.meals.toModel()

private fun List<FetchMealsResponse.MealResponse>.toModel(): List<Meal> =
    this.mapNotNull { meal -> runCatching { meal.toModel() }.getOrNull() }

private fun FetchMealsResponse.MealResponse.toModel(): Meal {
    val (breakfast, kcalOfBreakfast) = breakfast.toMenuAndKcal()
    val (lunch, kcalOfLunch) = lunch.toMenuAndKcal()
    val (dinner, kcalOfDinner) = dinner.toMenuAndKcal()

    return Meal(
        date = date.toLocalDate(),
        breakfast = breakfast,
        kcalOfBreakfast = kcalOfBreakfast,
        lunch = lunch,
        kcalOfLunch = kcalOfLunch,
        dinner = dinner,
        kcalOfDinner = kcalOfDinner,
    )
}

private fun List<String>?.toMenuAndKcal(): Pair<List<String>, String?> =
    orEmpty().dropLast(1) to this?.lastOrNull()
