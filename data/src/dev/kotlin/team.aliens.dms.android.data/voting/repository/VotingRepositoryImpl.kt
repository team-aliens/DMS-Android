package team.aliens.dms.android.data.voting.repository

import java.time.LocalDate
import team.aliens.dms.android.data.voting.mapper.toModel
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.VotingItem
import team.aliens.dms.android.network.voting.datasource.NetworkVotingDataSource
import java.util.UUID
import javax.inject.Inject

internal class VotingRepositoryImpl @Inject constructor(
    private val networkVotingDataSource: NetworkVotingDataSource,
) : VotingRepository() {
    override suspend fun fetchAllVoteSearch(): Result<List<AllVoteSearch>> = runCatching {
        networkVotingDataSource.fetchAllVoteSearch().toModel()
    }

    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): Result<List<VotingItem>> = runCatching {
        networkVotingDataSource.fetchCheckVotingItem(votingTopicId).toModel()
    }

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID): Result<Unit> = runCatching {
        networkVotingDataSource.fetchCreateVotingItem(votingTopicId, selectedId)
    }

    override suspend fun fetchDeleteVotingItem(voteId: UUID): Result<Unit> = runCatching {
        networkVotingDataSource.fetchDeleteVotingItem(voteId)
    }

    override suspend fun fetchModelStudentCandidates(requestDate: LocalDate): Result<List<ModelStudentCandidates>> = runCatching {
        networkVotingDataSource.fetchModelStudentCandidates(requestDate).toModel()
    }
}
