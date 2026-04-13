package team.aliens.dms.android.wear.model

import androidx.compose.runtime.Immutable

@Immutable
data class WearNoticeUiModel(
    val title: String,
    val dateText: String,
    val isImportant: Boolean,
)
