package team.aliens.local_domain.entity.notice

import java.util.*

data class NoticeListLocalEntity(
    val noticeValue: List<NoticeValue>,
) {
    data class NoticeValue(
        val id: UUID,
        val title: String,
        val createAt: String,
    )
}
