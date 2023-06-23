package team.aliens.dms_android.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import team.aliens.design_system.theme.DormTheme

internal val listFadeBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            DormTheme.colors.surface,
            Color.Transparent,
        ),
    )