package team.aliens.remote.model.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain._model.notice.FetchNoticeDetailsOutput

data class FetchNoticeDetailsResponse(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
)

internal fun FetchNoticeDetailsResponse.toDomain(): FetchNoticeDetailsOutput {
    return FetchNoticeDetailsOutput(
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}
