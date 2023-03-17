package team.aliens.design_system.extension

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun ColumnScope.Space(
    space: Dp = 0.dp,
    ratio: Float? = null,
) {
    ratio?.run {
        Spacer(modifier = Modifier.weight(ratio))
    }
    Spacer(modifier = Modifier.height(space))
}

@Composable
internal fun RowScope.Space(
    space: Dp = 0.dp,
    ratio: Float? = null,
) {
    ratio?.run {
        Spacer(modifier = Modifier.weight(ratio))
    }
    Spacer(modifier = Modifier.width(space))
}

@Composable
internal fun LazyListScope.Space(
    space: Dp = 0.dp,
    ratio: Float? = null,
) {
    ratio?.run {
        Spacer(modifier = Modifier)
    }
    Spacer(modifier = Modifier.height(space))
}