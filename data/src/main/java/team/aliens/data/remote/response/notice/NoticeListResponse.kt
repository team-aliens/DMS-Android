package team.aliens.data.remote.response.notice

import com.google.gson.annotations.SerializedName
import team.aliens.domain.entity.notice.NoticeListEntity
import java.util.*

data class NoticeListResponse(
    @SerializedName("notices") val notices: List<Notices>,
) {
    data class Notices(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("created_at") val createAt: String,
    )
}

fun NoticeListResponse.Notices.toEntity() =
    NoticeListEntity.NoticeValue(id = id, title = title, createAt = createAt)

fun NoticeListResponse.toEntity() = NoticeListEntity(notices = notices.map { it.toEntity() })
