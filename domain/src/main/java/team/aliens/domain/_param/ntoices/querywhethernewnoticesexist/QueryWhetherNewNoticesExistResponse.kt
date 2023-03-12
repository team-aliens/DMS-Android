package team.aliens.domain._param.ntoices.querywhethernewnoticesexist

/**
 * @author junsuPark
 * A response returned when querying whether new notices exist
 * @property whetherNewNotices a boolean value of whether new notices exists
 */
data class QueryWhetherNewNoticesExistResponse(
    val whetherNewNotices: Boolean,
)
