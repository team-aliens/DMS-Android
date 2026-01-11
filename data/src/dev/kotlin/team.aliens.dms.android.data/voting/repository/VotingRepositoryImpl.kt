package team.aliens.dms.android.data.voting.repository

import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.voting.mapper.toModel
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.VotingItem
import team.aliens.dms.android.network.voting.datasource.NetworkVotingDataSource
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

internal class VotingRepositoryImpl @Inject constructor(
    private val networkVotingDataSource: NetworkVotingDataSource,
) : VotingRepository() {
    override suspend fun fetchAllVoteSearch(): Result<List<AllVoteSearch>> =
        networkVotingDataSource.fetchAllVoteSearch().map { it.toModel() }

    override suspend fun fetchCheckVotingItem(votingTopicId: UUID): Result<List<VotingItem>> =
        networkVotingDataSource.fetchCheckVotingItem(votingTopicId).map { it.toModel() }

    override suspend fun fetchCreateVotingItem(votingTopicId: UUID, selectedId: UUID): Result<Unit> =
        networkVotingDataSource.fetchCreateVotingItem(votingTopicId, selectedId)

    override suspend fun fetchDeleteVotingItem(voteId: UUID): Result<Unit> =
        networkVotingDataSource.fetchDeleteVotingItem(voteId)

    override suspend fun fetchModelStudentCandidates(requestDate: LocalDate): Result<List<Student>> =
        networkVotingDataSource.fetchModelStudentCandidates(requestDate).map { it.toModel() }
}
