package team.aliens.remote.model.notice

import com.google.gson.annotations.SerializedName
import java.util.*

data class FetchNoticesResponse(
    @SerializedName("notices") val notices: List<Notice>,
) {
    data class Notice(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("created_at") val createdAt: String,
    )
}
