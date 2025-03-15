package team.aliens.dms.android.data.voting.model

import team.aliens.dms.android.data.registration.model.Vote

data class AllVoteSearch(
    val id: String,
    val topicName: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val voteType: Vote,
)
