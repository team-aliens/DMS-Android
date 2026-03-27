package team.aliens.dms.android.data.voting.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class VotingItem(
    val id: UUID,
    val votingOptionName: String,
)
