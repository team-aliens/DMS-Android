package team.aliens.dms.android.network.remains.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchAppliedRemainsOptionResponse(
    @SerializedName("id") val remainsOptionId: UUID,
    @SerializedName("title") val title: String,
)
