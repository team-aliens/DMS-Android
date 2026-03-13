package team.aliens.dms.android.data.voting.model

import java.util.UUID

data class ModelStudentCandidates(
    val id: UUID,
    val studentGcn: Long,
    val name: String,
    val profileImageUrl: String,
)
