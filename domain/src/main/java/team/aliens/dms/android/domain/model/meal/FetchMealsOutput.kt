package team.aliens.dms.android.domain.model.meal

/**
 * A response returned when fetching meals information
 * @property meals list of meals
 */
data class FetchMealsOutput(
    val meals: List<MealInformation>,
) {

    /**
     * A set of daily meal information
     * @property date date of the information
     * @property breakfast list of breakfast dishes
     * @property lunch list of lunch dishes
     * @property dinner list of dinner dishes
     */
    data class MealInformation(
        val date: String,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}

fun FetchMealsOutput.MealInformation.toModel(): Meal {
    return Meal(
        date = this.date,
        breakfast = this.breakfast,
        lunch = this.lunch,
        dinner = this.dinner,
    )
}

fun List<FetchMealsOutput.MealInformation>.toModel(): List<Meal> {
    return this.map(FetchMealsOutput.MealInformation::toModel)
}

fun List<FetchMealsOutput.MealInformation>.toTypedArray(): Array<Meal> {
    return this.toModel().toTypedArray()
}
