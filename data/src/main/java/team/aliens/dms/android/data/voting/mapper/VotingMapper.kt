package team.aliens.dms.android.data.voting.mapper

import java.util.UUID
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.VotingItem
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidatesResponse
import team.aliens.dms.android.shared.date.toLocalDateTime

internal fun FetchAllVoteSearchResponse.toModel(): List<AllVoteSearch> =
    this.votingTopics.map(FetchAllVoteSearchResponse.VoteSearchResponse::toModel)

private fun FetchAllVoteSearchResponse.VoteSearchResponse.toModel(): AllVoteSearch =
    AllVoteSearch(
        id = UUID.fromString(this.id),
        topicName = this.topicName,
        description = this.description,
        startTime = this.startTime.toLocalDateTime(),
        endTime = this.endTime.toLocalDateTime(),
        voteType = Vote.valueOf(this.voteType),
        isVoted = this.isVoted,
    )

internal fun FetchCheckVotingItemResponse.toModel(): List<VotingItem> =
    this.votingOptions.map(FetchCheckVotingItemResponse.VotingItemResponse::toModel)

private fun FetchCheckVotingItemResponse.VotingItemResponse.toModel(): VotingItem =
    VotingItem(
        id = id,
        votingOptionName = votingOptionName,
    )

internal fun FetchModelStudentCandidatesResponse.toModel(): List<ModelStudentCandidates> =
    this.students.map(FetchModelStudentCandidatesResponse.ModelStudentCandidatesResponse::toModel)

private fun FetchModelStudentCandidatesResponse.ModelStudentCandidatesResponse.toModel(): ModelStudentCandidates =
    ModelStudentCandidates(
        id = this.id,
        studentGcn = this.studentGcn,
        name = this.name,
        profileImageUrl = this.profileImageUrl,
    )
