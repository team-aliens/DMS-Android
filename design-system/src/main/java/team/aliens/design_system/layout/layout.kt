package team.aliens.design_system.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.design_system.modifier.dormGradientBackground
import team.aliens.design_system.theme.DormTheme

val topFadeBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            DormTheme.colors.background,
            Color.Transparent,
        ),
    )

val bottomFadeBrush: Brush
    @Composable get() = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            DormTheme.colors.background,
        ),
    )

@Composable
fun VerticallyFadedColumn(
    modifier: Modifier = Modifier,
    topDisappearanceSize: Dp = 24.dp,
    bottomDisappearanceSize: Dp = 24.dp,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topDisappearanceSize)
                    .dormGradientBackground(topFadeBrush),
            )
            Spacer(Modifier.weight(1f))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomDisappearanceSize)
                    .dormGradientBackground(bottomFadeBrush),
            )
        }
    }
}