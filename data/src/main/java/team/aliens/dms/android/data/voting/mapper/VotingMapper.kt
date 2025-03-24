package team.aliens.dms.android.data.voting.mapper

import java.util.UUID
import team.aliens.dms.android.shared.date.toLocalDate
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse

internal fun FetchAllVoteSearchResponse.toModel(): List<AllVoteSearch> =
    this.votingTopics.map(FetchAllVoteSearchResponse.VoteSearchResponse::toModel)

private fun FetchAllVoteSearchResponse.VoteSearchResponse.toModel(): AllVoteSearch =
    AllVoteSearch(
        id = UUID.fromString(this.id),
        topicName = this.topicName,
        description = this.description,
        startTime = this.startTime.toLocalDate(),
        endTime = this.endTime.toLocalDate(),
        voteType = Vote.valueOf(this.voteType),
    )

internal fun FetchCheckVotingItemResponse.toModel(): CheckVotingItem =
    CheckVotingItem(
        id = this.id,
        optionName = this.optionName,
    )
