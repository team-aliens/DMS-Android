package team.aliens.dms.android.data.notice.model

import java.time.LocalDateTime
import java.util.UUID

data class Notice(
    val id: UUID,
    val title: String,
    val content: String?,
    val createdAt: LocalDateTime,
)
