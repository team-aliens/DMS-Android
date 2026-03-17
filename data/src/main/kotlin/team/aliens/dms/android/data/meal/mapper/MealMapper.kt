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
    this.map(FetchMealsResponse.MealResponse::toModel)

private fun FetchMealsResponse.MealResponse.toModel(): Meal = Meal(
    date = this.date.toLocalDate(),
    breakfast = this.breakfast.dropLast(1),
    kcalOfBreakfast = this.breakfast.last(),
    lunch = this.lunch.dropLast(1),
    kcalOfLunch = this.lunch.last(),
    dinner = this.dinner.dropLast(1),
    kcalOfDinner = this.dinner.last(),
)
