package team.aliens.dms.android.feature.voting.navigation

import java.util.UUID

interface VotingNavigator {
    fun openVoting()
    fun openVotingApproval(voteId: UUID)
    fun openVotingModelStudent()
    fun openVotingSelected(voteId: UUID)
    fun openVotingStudent(voteId: UUID)
    fun navigateUp()
}
