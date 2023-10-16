package team.aliens.dms.android.network.remains.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchRemainsOptionsResponse(
    @SerializedName("remain_options") val remainsOptionResponse: List<RemainsOptionResponse>,
) {
    data class RemainsOptionResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("is_applied") val applied: Boolean,
    )
}
