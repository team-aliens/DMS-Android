package team.aliens.domain._param.meals.querymeals

/**
 * @author junsuPark
 * A response returned when querying meals information
 * [meals] list of meals
 */
data class QueryMealsResponse(
    val meals: List<MealInformation>,
) {

    /**
     * @author junsuPark
     * A set of daily meal information
     * [date] date of the information
     * [breakfast] list of breakfast dishes
     * [lunch] list of lunch dishes
     * [dinner] list of dinner dishes
     */
    data class MealInformation(
        val date: String,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}
