package team.aliens.dms.android.network.voting.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchCheckVotingItemResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("option_name") val optionName: String,
)
