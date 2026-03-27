package team.aliens.dms.android.data.notice.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class LatestNotice(
    val id: UUID? = null,
    val title: String = "",
)
