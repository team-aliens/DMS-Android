package team.aliens.dms.android.network.voting.datasource

import java.time.LocalDate
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidatesResponse
import java.util.UUID

abstract class NetworkVotingDataSource {

    abstract suspend fun fetchAllVoteSearch(): Result<FetchAllVoteSearchResponse>

    abstract suspend fun fetchCheckVotingItem(votingTopicId: UUID): Result<FetchCheckVotingItemResponse>

    abstract suspend fun fetchCreateVotingItem(
        votingTopicId: UUID,
        selectedId: UUID,
    ): Result<Unit>

    abstract suspend fun fetchDeleteVotingItem(voteId: UUID): Result<Unit>

    abstract suspend fun fetchModelStudentCandidates(
        requestDate: LocalDate,
    ): Result<FetchModelStudentCandidatesResponse>
}
