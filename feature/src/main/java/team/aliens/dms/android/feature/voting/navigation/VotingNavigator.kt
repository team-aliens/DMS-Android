package team.aliens.dms.android.feature.voting.navigation

import team.aliens.dms.android.data.voting.model.AllVoteSearch

interface VotingNavigator {
    fun openVoting()
    fun openVotingApproval(voteOption: AllVoteSearch)
    fun openVotingModelStudent()
    fun openVotingSelected(voteOption: AllVoteSearch)
    fun openVotingStudent(voteOption: AllVoteSearch)
    fun navigateUp()
}
