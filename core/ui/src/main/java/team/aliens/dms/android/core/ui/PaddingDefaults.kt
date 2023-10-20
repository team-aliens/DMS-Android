package team.aliens.dms.android.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// TODO: 스크린 방향 대응
// ScreenTop, ScreenStart, ScreenEnd, ScreenBottom 등 dp 추가
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
        start: Dp = Large,
        top: Dp = Medium,
        end: Dp = Large,
        bottom: Dp = Medium,
    ): PaddingValues = PaddingValues(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
    )

    fun screen(
        horizontal: Dp = Large,
        vertical: Dp = Medium,
    ): PaddingValues = screen(
        start = horizontal,
        top = vertical,
        end = horizontal,
        bottom = horizontal,
    )
}

fun Modifier.screenPadding(
    start: Dp = PaddingDefaults.Large,
    top: Dp = PaddingDefaults.Medium,
    end: Dp = PaddingDefaults.Large,
    bottom: Dp = PaddingDefaults.Medium,
): Modifier = padding(
    start = start,
    top = top,
    end = end,
    bottom = bottom,
)

fun Modifier.screenPadding(
    horizontal: Dp = PaddingDefaults.Large,
    vertical: Dp = PaddingDefaults.Medium,
): Modifier = padding(
    horizontal = horizontal,
    vertical = vertical,
)
