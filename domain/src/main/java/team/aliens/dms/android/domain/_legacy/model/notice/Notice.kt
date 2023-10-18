package team.aliens.dms.android.domain._legacy.model.notice

import java.util.*

/**
 * A notice entity contains title, content, and created at
 * @property id notice's unique id
 * @property title notice's title
 * @property content notice's content, default is null
 * @property createdAt when notice was created at
 */
data class Notice(
    val id: UUID?,
    val title: String,
    val content: String? = null,
    val createdAt: String,
)
