package team.aliens.dms_android.feature.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import team.aliens.dms_android.design_system.theme.DormTheme

internal val listFadeBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            DormTheme.colors.background,
            Color.Transparent,
        ),
    )