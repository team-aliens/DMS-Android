package team.aliens.dms.android.network.voting.datasource

import java.time.LocalDate
import team.aliens.dms.android.network.voting.apiservice.VotingApiService
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidatesResponse
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import java.util.UUID
import javax.inject.Inject

internal class NetworkVotingDataSourceImpl @Inject constructor(
    private val votingApiService: VotingApiService,
) : NetworkVotingDataSource() {
    override suspend fun fetchAllVoteSearch(): Result<FetchAllVoteSearchResponse> =
        suspendRunCatching { votingApiService.fetchAllVoteSearch() }

    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): Result<FetchCheckVotingItemResponse> =
        suspendRunCatching { votingApiService.fetchCheckVotingItem(votingTopicId) }

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID): Result<Unit> =
        suspendRunCatching {
            votingApiService.fetchCreateVotingItem(votingTopicId, selectedId)
        }

    override suspend fun fetchDeleteVotingItem(voteId: UUID): Result<Unit> =
        suspendRunCatching { votingApiService.fetchDeleteVotingItem(voteId) }

    override suspend fun fetchModelStudentCandidates(
        requestDate: LocalDate,
    ): Result<FetchModelStudentCandidatesResponse> =
        suspendRunCatching { votingApiService.fetchModelStudentCandidates(requestDate) }
}
