package team.aliens.dms.android.data.voting.mapper

import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse

internal fun FetchAllVoteSearchResponse.toModel(): List<AllVoteSearch> =
    this.votingTopics.map(FetchAllVoteSearchResponse.VoteSearchResponse::toModel)

private fun FetchAllVoteSearchResponse.VoteSearchResponse.toModel(): AllVoteSearch =
    AllVoteSearch(
        id = this.id,
        topicName = this.topicName,
        description = this.description,
        startTime = this.startTime,
        endTime = this.endTime,
        voteType = this.voteType,
    )

internal fun FetchCheckVotingItemResponse.toModel(): CheckVotingItem = CheckVotingItem(
        id = this.id,
        optionName = this.optionName,
    )
