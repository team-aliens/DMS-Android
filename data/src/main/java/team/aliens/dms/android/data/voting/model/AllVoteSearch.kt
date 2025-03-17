package team.aliens.dms.android.data.voting.model

import org.threeten.bp.LocalDate

data class AllVoteSearch(
    val id: String,
    val topicName: String,
    val description: String,
    val startTime: LocalDate,
    val endTime: LocalDate,
    val voteType: Vote,
)
