package team.aliens.domain._model.notice

/**
 * A response returned when fetching notice details
 * @property title the detail's title
 * @property content the detail's content
 * @property createdAt when the detail was created at
 */
data class FetchNoticeDetailsOutput(
    val title: String,
    val content: String,
    val createdAt: String,
)
