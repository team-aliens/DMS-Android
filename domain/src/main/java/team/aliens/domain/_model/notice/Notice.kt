package team.aliens.domain._model.notice

import java.util.*

/**
 * A notice entity contains title, content, and created at
 * @property id notice's unique id
 * @property title notice's title
 * @property content notice's content, can be null
 * @property createdAt when notice was created at
 */
data class Notice(
    val id: UUID,
    val title: String,
    val content: String?,
    val createdAt: String,
)
