package team.aliens.domain._param.meal

/**
 * @author junsuPark
 * A response returned when querying meals information
 * @property meals list of meals
 */
data class QueryMealsResponse(
    val meals: List<MealInformation>,
) {

    /**
     * @author junsuPark
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
