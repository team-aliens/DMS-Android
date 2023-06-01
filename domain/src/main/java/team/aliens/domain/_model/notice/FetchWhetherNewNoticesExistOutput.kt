package team.aliens.domain._model.notice

/**
 * A response returned when fetching whether new notices exist
 * @property newNoticesExists a boolean value of whether new notices exists
 */
data class FetchWhetherNewNoticesExistOutput(
    val newNoticesExists: Boolean,
)
