package team.aliens.dms.android.data.voting.repository

import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.data.voting.mapper.toModel
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.network.voting.datasource.NetworkVotingDataSource
import java.util.UUID
import javax.inject.Inject

internal class VotingRepositoryImpl @Inject constructor(
    private val networkVotingDataSource: NetworkVotingDataSource,
) : VotingRepository() {
    override suspend fun fetchAllVoteSearch(): List<AllVoteSearch> =
        networkVotingDataSource.fetchAllVoteSearch().toModel()

    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): CheckVotingItem =
        networkVotingDataSource.fetchCheckVotingItem(votingTopicId).toModel()

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID) {
        networkVotingDataSource.fetchCreateVotingItem(votingTopicId, selectedId)
    }

    override suspend fun fetchDeleteVotingItem(voteId: UUID) {
        networkVotingDataSource.fetchDeleteVotingItem(voteId)
    }

    override suspend fun fetchModelStudentCandidates(requestDate: LocalDateTime): ModelStudentCandidates =
        networkVotingDataSource.fetchModelStudentCandidates(requestDate).toModel()

}
