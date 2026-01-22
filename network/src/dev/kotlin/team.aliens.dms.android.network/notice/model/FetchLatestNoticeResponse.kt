package team.aliens.dms.android.network.notice.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchLatestNoticeResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("title") val title: String,
)
