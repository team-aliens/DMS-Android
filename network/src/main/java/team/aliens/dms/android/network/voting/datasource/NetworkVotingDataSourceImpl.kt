package team.aliens.dms.android.network.voting.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.voting.apiservice.VotingApiService
import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

internal class NetworkVotingDataSourceImpl @Inject constructor(
    private val votingApiService: VotingApiService,
) : NetworkVotingDataSource() {
    override suspend fun fetchAllVoteSearch(): FetchAllVoteSearchResponse =
        handleNetworkRequest { votingApiService.fetchAllVoteSearch() }

    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): FetchCheckVotingItemResponse =
        handleNetworkRequest { votingApiService.fetchCheckVotingItem(votingTopicId) }

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID): Unit =
        handleNetworkRequest { votingApiService.fetchCreateVotingItem(votingTopicId, selectedId) }

    override suspend fun fetchDeleteVotingItem(voteId: UUID): Unit =
        handleNetworkRequest { votingApiService.fetchDeleteVotingItem(voteId) }

    override suspend fun fetchModelStudentCandidates(requestDate: LocalDate) {
        handleNetworkRequest { votingApiService.fetchModelStudentCandidates(requestDate) }
    }


}
