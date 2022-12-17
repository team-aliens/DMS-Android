package com.example.data.remote.response.notice

import com.example.domain.entity.notice.NoticeListEntity
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class NoticeListResponse(
    @SerializedName("notices") val notices: List<Notices>
) {
    data class Notices(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("create_at") val createAt: String,
    )
}

fun NoticeListResponse.Notices.toEntity() =
    NoticeListEntity.NoticeValue(
        id = id,
        title = title,
        createAt = createAt
    )

fun NoticeListResponse.toEntity() =
    NoticeListEntity(
        notices = notices.map { it.toEntity() }
    )
