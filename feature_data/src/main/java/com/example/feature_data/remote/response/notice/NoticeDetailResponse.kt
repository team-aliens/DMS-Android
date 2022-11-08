package com.example.feature_data.remote.response.notice

import com.example.feature_domain.entity.notice.NoticeDetailEntity
import com.example.feature_domain.entity.notice.NoticeListEntity
import com.example.local_database.entity.notice.NoticeListRoomEntity
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
