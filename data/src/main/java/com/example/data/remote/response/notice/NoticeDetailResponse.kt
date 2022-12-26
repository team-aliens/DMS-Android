package com.example.data.remote.response.notice

import com.example.domain.entity.notice.NoticeDetailEntity
import com.google.gson.annotations.SerializedName

data class NoticeDetailResponse(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createAt: String,
)

fun NoticeDetailResponse.toEntity() =
    NoticeDetailEntity(
        noticeId = null,
        title = title,
        content = content,
        createAt = createAt
    )
