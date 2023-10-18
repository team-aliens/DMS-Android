package team.aliens.dms.android.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import team.aliens.dms.android.designsystem.theme.DmsTheme

@Stable
val verticalFadeOutBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            DmsTheme.colors.background,
            Color.Transparent,
        ),
    )

@Stable
val verticalFadeInBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            DmsTheme.colors.background,
        ),
    )
