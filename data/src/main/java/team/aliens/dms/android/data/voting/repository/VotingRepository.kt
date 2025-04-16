package team.aliens.dms.android.data.voting.repository

import org.threeten.bp.LocalDate
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.VotingItem
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import java.util.UUID

abstract class VotingRepository {

    abstract suspend fun fetchAllVoteSearch(): List<AllVoteSearch>

    abstract suspend fun fetchCheckVotingItem(votingTopicId: UUID): VotingItem

    abstract suspend fun fetchCreateVotingItem(
        votingTopicId: UUID,
        selectedId: UUID,
    )

    abstract suspend fun fetchDeleteVotingItem(voteId: UUID)

    abstract suspend fun fetchModelStudentCandidates(requestDate: LocalDate): List<ModelStudentCandidates>
}
