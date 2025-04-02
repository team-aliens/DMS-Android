package team.aliens.dms.android.feature.voting.navigation

import java.util.UUID

interface VotingNavigator {
    fun openVoting()
    fun openVotingApproval(voteOptionId: UUID)
    fun openVotingModelStudent()
    fun openVotingSelected(voteOptionId: UUID)
    fun openVotingStudent(voteOptionId: UUID)
    fun navigateUp()
}
