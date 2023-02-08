package team.aliens.domain.entity.notice

import java.util.UUID

data class NoticeDetailEntity(
    val noticeId: UUID?,
    val title: String,
    val content: String,
    val createAt: String,
)
