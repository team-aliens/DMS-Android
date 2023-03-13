package team.aliens.domain._param.notice.querynotices

import java.util.*

/**
 * @author junsuPark
 * A response returned when querying notices
 * @property notices list of notices of the user
 */
data class QueryNoticesResponse(
    val notices: List<NoticeInformation>,
) {

    /**
     * @author junsuPark
     * @property id id of unique notice
     * @property title title of the notice
     * @property createdAt created time of the notice
     */
    data class NoticeInformation(
        val id: UUID,
        val title: String,
        val createdAt: String,
    )
}
