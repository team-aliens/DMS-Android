package com.example.domain.entity.notice

import java.util.UUID

data class NoticeListEntity(
    val notices: List<NoticeValue>
) {
    data class NoticeValue(
        val id: UUID,
        val title: String,
        val createAt: String,
    )
}
