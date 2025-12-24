package team.aliens.dms.android.data.remains.model

import java.util.UUID

data class RemainsOption(
    val id: UUID,
    val title: String,
    val description: String,
    val applied: Boolean,
)
