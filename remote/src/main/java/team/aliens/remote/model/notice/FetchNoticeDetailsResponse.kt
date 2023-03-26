package team.aliens.remote.model.notice

import com.google.gson.annotations.SerializedName

data class FetchNoticeDetailsResponse(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
)
