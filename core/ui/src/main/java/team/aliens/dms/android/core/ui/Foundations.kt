package team.aliens.dms.android.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

object PaddingDefaults {

    val None = 0.dp
    val ExtraSmall = 4.dp
    val Small = 8.dp
    val Medium = 12.dp
    val Large = 16.dp
    val ExtraLarge = 20.dp

    val Horizontal = PaddingValues(
        start = Large,
        top = None,
        end = Large,
        bottom = None,
    )

    val Vertical = PaddingValues(
        start = None,
        top = Medium,
        end = None,
        bottom = Medium,
    )

    fun screen(
        layoutDirection: LayoutDirection,
        start: Dp = Horizontal.calculateStartPadding(layoutDirection),
        top: Dp = Vertical.calculateTopPadding(),
        end: Dp = Horizontal.calculateEndPadding(layoutDirection),
        bottom: Dp = Vertical.calculateBottomPadding(),
    ): PaddingValues = PaddingValues(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
    )

    fun screen(
        layoutDirection: LayoutDirection,
        horizontal: Dp = Large,
        vertical: Dp = Medium,
    ): PaddingValues = screen(
        layoutDirection = layoutDirection,
        start = horizontal,
        top = vertical,
        end = horizontal,
        bottom = horizontal,
    )
}
