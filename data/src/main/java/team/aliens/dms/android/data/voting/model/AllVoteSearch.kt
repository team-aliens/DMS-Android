package team.aliens.dms.android.data.voting.model

import org.threeten.bp.LocalDate
import java.util.UUID

data class AllVoteSearch(
    val id: UUID,
    val topicName: String,
    val description: String,
    val startTime: LocalDate,
    val endTime: LocalDate,
    val voteType: Vote,
)
