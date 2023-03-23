package team.aliens.domain._model.notice

import java.util.*

/**
 * A response returned when fetching notices
 * @property notices list of notices of the user
 */
data class FetchNoticesOutput(
    val notices: List<NoticeInformation>,
) {

    /**
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
