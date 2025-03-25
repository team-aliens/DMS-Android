package team.aliens.dms.android.network.voting.datasource

import team.aliens.dms.android.network.voting.model.FetchAllVoteSearchResponse
import team.aliens.dms.android.network.voting.model.FetchCheckVotingItemResponse
import team.aliens.dms.android.network.voting.model.FetchModelStudentCandidates
import java.time.LocalDate
import java.util.UUID

abstract class NetworkVotingDataSource {

    abstract suspend fun fetchAllVoteSearch(): FetchAllVoteSearchResponse

    abstract suspend fun fetchCheckVotingItem(votingTopicId: UUID): FetchCheckVotingItemResponse

    abstract suspend fun fetchCreateVotingItem(
        votingTopicId: UUID,
        selectedId: UUID,
    )

    abstract suspend fun fetchDeleteVotingItem(voteId: UUID)

    abstract suspend fun fetchModelStudentCandidates(requestDate: LocalDate): FetchModelStudentCandidates
}
