package team.aliens.domain._param.ntoices.querynotices

import java.util.*

/**
 * @author junsuPark
 * A response returned when querying notices
 * [notices] list of notices of the user
 */
data class QueryNoticesResponse(
    val notices: List<NoticeInformation>,
) {

    /**
     * @author junsuPark
     * [id] id of unique notice
     * [title] title of the notice
     * [createdAt] created time of the notice
     */
    data class NoticeInformation(
        val id: UUID,
        val title: String,
        val createdAt: String,
    )
}
