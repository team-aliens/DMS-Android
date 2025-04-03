package team.aliens.dms.android.data.voting.mapper

import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidatesResponse
import team.aliens.dms.android.shared.date.toLocalDateTime
import java.util.UUID

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
    )

internal fun FetchCheckVotingItemResponse.toModel(): CheckVotingItem =
    CheckVotingItem(
        id = this.id,
        optionName = this.optionName,
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
