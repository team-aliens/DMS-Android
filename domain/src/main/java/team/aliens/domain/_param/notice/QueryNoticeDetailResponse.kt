package team.aliens.domain._param.notice

/**
 * @author junsuPark
 * A response returned when querying notice detail
 * @property title the detail's title
 * @property content the detail's content
 * @property createdAt when the detail was created at
 */
data class QueryNoticeDetailResponse(
    val title: String,
    val content: String,
    val createdAt: String,
)
