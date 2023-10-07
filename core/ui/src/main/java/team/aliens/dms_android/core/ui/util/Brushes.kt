package team.aliens.dms_android.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import team.aliens.design_system.theme.DormTheme

@Stable
val verticalFadeOutBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            DormTheme.colors.background,
            Color.Transparent,
        ),
    )

@Stable
val verticalFadeInBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            DormTheme.colors.background,
        ),
    )