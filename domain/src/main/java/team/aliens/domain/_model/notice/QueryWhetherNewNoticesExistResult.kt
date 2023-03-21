package team.aliens.domain._model.notice

/**
 * @author junsuPark
 * A response returned when querying whether new notices exist
 * @property whetherNewNotices a boolean value of whether new notices exists
 */
data class QueryWhetherNewNoticesExistResult(
    val whetherNewNotices: Boolean,
)
