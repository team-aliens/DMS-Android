package team.aliens.dms.android.wear.model

import androidx.compose.runtime.Immutable

@Immutable
data class WearSnapshot(
    val primaryMealLabel: String,
    val primaryMealMenu: List<String>,
    val statusTitle: String,
    val statusValue: String,
    val syncedAt: String,
    val notices: List<WearNoticeUiModel>,
)
