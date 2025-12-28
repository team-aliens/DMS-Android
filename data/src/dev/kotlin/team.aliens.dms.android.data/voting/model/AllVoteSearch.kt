package team.aliens.dms.android.data.voting.model

import team.aliens.dms.android.shared.date.util.now
import java.time.LocalDateTime
import java.util.UUID

data class AllVoteSearch(
    val id: UUID = UUID.randomUUID(),
    val topicName: String = "",
    val description: String = "",
    val startTime: LocalDateTime = now,
    val endTime: LocalDateTime = now,
    val voteType: Vote = Vote.STUDENT_VOTE,
    val isVoted: Boolean = false,
)
