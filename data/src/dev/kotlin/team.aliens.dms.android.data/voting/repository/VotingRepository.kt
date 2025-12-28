package team.aliens.dms.android.data.voting.repository

import team.aliens.dms.android.data.student.model.Student
import java.time.LocalDate
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.VotingItem
import java.util.UUID

abstract class VotingRepository {

    abstract suspend fun fetchAllVoteSearch(): Result<List<AllVoteSearch>>

    abstract suspend fun fetchCheckVotingItem(votingTopicId: UUID): Result<List<VotingItem>>

    abstract suspend fun fetchCreateVotingItem(
        votingTopicId: UUID,
        selectedId: UUID,
    ): Result<Unit>

    abstract suspend fun fetchDeleteVotingItem(voteId: UUID): Result<Unit>

    abstract suspend fun fetchModelStudentCandidates(requestDate: LocalDate): Result<List<Student>>
}
