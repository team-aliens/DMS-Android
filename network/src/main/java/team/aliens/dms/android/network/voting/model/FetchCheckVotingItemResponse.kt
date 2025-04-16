package team.aliens.dms.android.network.voting.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FetchCheckVotingItemResponse(
    @SerializedName("voting_options") val votingOptions: List<VotingItemResponse>
) {
    data class VotingItemResponse(
        @SerializedName("id") val id: UUID,
        @SerializedName("voting_option_name") val votingOptionName: String,
    )
}
