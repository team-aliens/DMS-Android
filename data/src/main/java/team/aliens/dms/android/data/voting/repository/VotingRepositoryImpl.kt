package team.aliens.dms.android.data.voting.repository

import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.network.voting.datasource.NetworkVotingDataSource
import java.util.UUID
import javax.inject.Inject

internal class VotingRepositoryImpl @Inject constructor(
    private val networkVotingDataSource: NetworkVotingDataSource,
): VotingRepository() {
    override suspend fun fetchAllVoteSearch(): List<AllVoteSearch> =
        networkVotingDataSource.fetchAllVoteSearch()


    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): List<CheckVotingItem> {
        
    }

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID) {
        
    }

    override suspend fun fetchDeleteVotingItem(voteId: UUID) {
        
    }

}
