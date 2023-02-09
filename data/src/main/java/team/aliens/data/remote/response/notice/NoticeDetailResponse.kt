package team.aliens.data.remote.response.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.notice.NoticeDetailEntity

data class NoticeDetailResponse(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createAt: String,
)

fun NoticeDetailResponse.toEntity() =
    NoticeDetailEntity(noticeId = null, title = title, content = content, createAt = createAt)
