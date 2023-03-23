package team.aliens.domain._model.notice

/**
 * A response returned when fetching notice detail
 * @property title the detail's title
 * @property content the detail's content
 * @property createdAt when the detail was created at
 */
data class FetchNoticeDetailOutput(
    val title: String,
    val content: String,
    val createdAt: String,
)
