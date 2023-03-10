package team.aliens.domain._param.ntoices.querynoticedetail

/**
 * @author junsuPark
 * A response returned when querying notice detail
 * [title] the detail's title
 * [content] the detail's content
 * [createdAt] when the detail was created at
 */
data class QueryNoticeDetailResponse(
    val title: String,
    val content: String,
    val createdAt: String,
)
