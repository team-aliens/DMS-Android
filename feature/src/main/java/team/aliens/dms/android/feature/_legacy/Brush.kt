package team.aliens.dms.android.feature._legacy

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import team.aliens.dms.android.core.designsystem.DmsTheme

internal val listFadeBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            DmsTheme.colorScheme.background,
            Color.Transparent,
        ),
    )