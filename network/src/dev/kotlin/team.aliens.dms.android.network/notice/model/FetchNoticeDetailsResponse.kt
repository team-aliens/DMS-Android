package team.aliens.dms.android.network.notice.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchNoticeDetailsResponse(
    @SerializedName("id") val noticeId: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
)
