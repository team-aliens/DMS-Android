package team.aliens.dms.android.network.notice.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchNoticesResponse(
    @SerializedName("notices") val notices: List<NoticeResponse>,
) {
    data class NoticeResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("created_at") val createdAt: String,
    )
}
