package team.aliens.dms.android.data.notice.model

import org.threeten.bp.LocalDateTime
import java.util.UUID

data class Notice(
    val id: UUID,
    val title: String,
    val content: String?,
    val createdAt: LocalDateTime,
)
