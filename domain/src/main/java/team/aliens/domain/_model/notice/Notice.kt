package team.aliens.domain._model.notice

/**
 * @author junsuPark
 * A notice entity contains title, content, and created at
 * @property title notice's title
 * @property content notice's property
 * @property createdAt when notice was created at
 */
data class Notice(
    val title: String,
    val content: String,
    val createdAt: String,
)
