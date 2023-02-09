package team.aliens.domain.entity.notice

import java.util.*

data class NoticeListEntity(
    val notices: List<NoticeValue>,
) {
    data class NoticeValue(
        val id: UUID,
        val title: String,
        val createAt: String,
    )
}
