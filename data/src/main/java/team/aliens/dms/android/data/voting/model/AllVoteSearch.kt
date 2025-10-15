package team.aliens.dms.android.data.voting.model

import org.threeten.bp.LocalDateTime
import java.util.UUID

data class AllVoteSearch(
    val id: UUID,
    val topicName: String,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val voteType: Vote,
    val isVoted: Boolean,
)
