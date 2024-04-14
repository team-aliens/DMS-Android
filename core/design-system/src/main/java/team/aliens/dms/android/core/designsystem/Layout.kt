package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticallyFadedColumn(
    modifier: Modifier = Modifier,
    topDisappearanceSize: Dp = 24.dp,
    bottomDisappearanceSize: Dp = 24.dp,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    topFadeBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            DmsTheme.colorScheme.background,
            Color.Transparent,
        ),
    ),
    bottomFadeBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            DmsTheme.colorScheme.background,
        ),
    ),
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
                    .gradientBackground(topFadeBrush),
            )
            Spacer(Modifier.weight(1f))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomDisappearanceSize)
                    .gradientBackground(bottomFadeBrush),
            )
        }
    }
}

@Composable
fun VerticallyFadedLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(vertical = 24.dp),
    reverseLayout: Boolean = false,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    topDisappearanceSize: Dp = 24.dp,
    bottomDisappearanceSize: Dp = 24.dp,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    topFadeBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            DmsTheme.colorScheme.background,
            Color.Transparent,
        ),
    ),
    bottomFadeBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            DmsTheme.colorScheme.background,
        ),
    ),
    content: LazyListScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = content,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topDisappearanceSize)
                    .gradientBackground(topFadeBrush),
            )
            Spacer(Modifier.weight(1f))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomDisappearanceSize)
                    .gradientBackground(bottomFadeBrush),
            )
        }
    }
}

private fun Modifier.gradientBackground(
    backgroundColor: Any,
) = run {
    when (backgroundColor) {
        is Color -> this.background(
            color = backgroundColor,
        )

        is Brush -> this.background(
            brush = backgroundColor,
        )

        else -> throw IllegalArgumentException()
    }
}
