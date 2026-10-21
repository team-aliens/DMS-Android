package team.aliens.dms.android.wear.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class WearSnapshot(
    val primaryMealLabel: String,
    val primaryMealMenu: ImmutableList<String>,
    val statusTitle: String,
    val statusValue: String,
    val syncedAt: String,
    val notices: ImmutableList<WearNoticeUiModel>,
)
