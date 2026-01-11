package team.aliens.dms.android.network.voting.datasource

import java.time.LocalDate
import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.voting.apiservice.VotingApiService
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidatesResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkVotingDataSourceImpl @Inject constructor(
    private val votingApiService: VotingApiService,
) : NetworkVotingDataSource() {
    override suspend fun fetchAllVoteSearch(): Result<FetchAllVoteSearchResponse> =
        runCatching { handleNetworkRequest { votingApiService.fetchAllVoteSearch() } }

    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): Result<FetchCheckVotingItemResponse> =
        runCatching { handleNetworkRequest { votingApiService.fetchCheckVotingItem(votingTopicId) } }

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID): Result<Unit> =
        runCatching {
            handleNetworkRequest { votingApiService.fetchCreateVotingItem(votingTopicId, selectedId) }
                ?.getOrThrow() ?: Unit
        }

    override suspend fun fetchDeleteVotingItem(voteId: UUID): Result<Unit> =
        runCatching { handleNetworkRequest { votingApiService.fetchDeleteVotingItem(voteId) } }
            .map { Unit }

    override suspend fun fetchModelStudentCandidates(
        requestDate: LocalDate,
    ): Result<FetchModelStudentCandidatesResponse> =
        runCatching { handleNetworkRequest { votingApiService.fetchModelStudentCandidates(requestDate) } }
}
