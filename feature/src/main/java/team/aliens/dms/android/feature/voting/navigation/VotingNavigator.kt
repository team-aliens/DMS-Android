package team.aliens.dms.android.feature.voting.navigation

import java.util.UUID

interface VotingNavigator {
    fun openVoting()
    fun openVotingApproval(voteOptionId: UUID, voteTopicTitle: String)
    fun openVotingModelStudent(voteOptionId: UUID, voteTopicTitle: String)
    fun openVotingSelected(voteOptionId: UUID, voteTopicTitle: String)
    fun openVotingStudent(voteOptionId: UUID, voteTopicTitle: String)
    fun navigateUp()
}
