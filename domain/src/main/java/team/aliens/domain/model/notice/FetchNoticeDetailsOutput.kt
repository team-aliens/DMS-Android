package team.aliens.domain.model.notice

import java.util.*

/**
 * A response returned when fetching notice details
 * @property id the notice's id
 * @property title the detail's title
 * @property content the detail's content
 * @property createdAt when the detail was created at
 */
data class FetchNoticeDetailsOutput(
    val id: UUID,
    val title: String,
    val content: String,
    val createdAt: String,
)

fun FetchNoticeDetailsOutput.toModel(): Notice {
    return Notice(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}
