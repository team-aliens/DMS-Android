package team.aliens.remote.model.remains

import com.google.gson.annotations.SerializedName
import java.util.*

data class FetchRemainsOptionsResponse(
    @SerializedName("remain_options") val remainOptions: List<RemainOption>,
) {
    data class RemainOption(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("is_applied") val isApplied: Boolean,
    )
}
