package team.aliens.network.model.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain.model.notice.FetchNoticeDetailsOutput
import java.util.*

data class FetchNoticeDetailsResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
)

internal fun FetchNoticeDetailsResponse.toDomain(): FetchNoticeDetailsOutput {
    return FetchNoticeDetailsOutput(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAt = this.createdAt,
    )
}
