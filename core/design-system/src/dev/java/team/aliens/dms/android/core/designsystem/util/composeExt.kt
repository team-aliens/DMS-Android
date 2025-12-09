package team.aliens.dms.android.core.designsystem.util

import androidx.compose.ui.Modifier

fun Modifier.modifyIf(
    condition: Boolean,
    modify: Modifier.() -> Modifier,
): Modifier = if (condition) modify() else this


