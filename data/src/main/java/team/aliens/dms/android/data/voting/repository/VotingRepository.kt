package team.aliens.dms.android.data.voting.repository

import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import java.util.UUID

abstract class VotingRepository {

    abstract suspend fun fetchAllVoteSearch(): List<AllVoteSearch>

    abstract suspend fun fetchCheckVotingItem(votingTopicId: UUID): CheckVotingItem

    abstract suspend fun fetchCreateVotingItem(
        votingTopicId: UUID,
        selectedId: UUID,
    )

    abstract suspend fun fetchDeleteVotingItem(voteId: UUID)
}
