package team.aliens.dms.android.network.voting.model

import com.google.gson.annotations.SerializedName

data class FetchAllVoteSearchResponse(
    @SerializedName("voting_topics") val votingTopics: List<VoteSearchResponse>,
) {
    data class VoteSearchResponse(
        @SerializedName("id") val id: String,
        @SerializedName("topic_name") val topicName: String,
        @SerializedName("description") val description: String,
        @SerializedName("start_time") val startTime: String,
        @SerializedName("end_time") val endTime: String,
        @SerializedName("vote_type") val voteType: String,
        @SerializedName("is_voted") val isVoted: Boolean,
    )
}
