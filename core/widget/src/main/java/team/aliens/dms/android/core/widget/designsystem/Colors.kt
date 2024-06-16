package team.aliens.dms.android.core.widget.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.glance.unit.ColorProvider
import team.aliens.dms.android.core.designsystem.ErrorDarken2
import team.aliens.dms.android.core.designsystem.ErrorDefault
import team.aliens.dms.android.core.designsystem.ErrorLighten2
import team.aliens.dms.android.core.designsystem.Gray1
import team.aliens.dms.android.core.designsystem.Gray10
import team.aliens.dms.android.core.designsystem.Gray2
import team.aliens.dms.android.core.designsystem.Gray3
import team.aliens.dms.android.core.designsystem.Gray5
import team.aliens.dms.android.core.designsystem.Gray6
import team.aliens.dms.android.core.designsystem.Gray7
import team.aliens.dms.android.core.designsystem.Gray9
import team.aliens.dms.android.core.designsystem.PrimaryDarken2
import team.aliens.dms.android.core.designsystem.PrimaryDefault
import team.aliens.dms.android.core.designsystem.PrimaryLighten2

data class ColorProviders(
    val primary: ColorProvider,
    val onPrimary: ColorProvider,
    val primaryContainer: ColorProvider,
    val onPrimaryContainer: ColorProvider,
    val error: ColorProvider,
    val onError: ColorProvider,
    val errorContainer: ColorProvider,
    val onErrorContainer: ColorProvider,
    val background: ColorProvider,
    val onBackground: ColorProvider,
    val backgroundVariant: ColorProvider,
    val onBackgroundVariant: ColorProvider,
    val surface: ColorProvider,
    val onSurface: ColorProvider,
    val surfaceVariant: ColorProvider,
    val onSurfaceVariant: ColorProvider,
    val icon: ColorProvider,
    val line: ColorProvider,
)

fun dynamicThemeColorProviders(): ColorProviders {
    return ColorProviders(
        primary = ColorProvider(PrimaryDefault),
        onPrimary = ColorProvider(Gray1),
        primaryContainer = ColorProvider(PrimaryLighten2),
        onPrimaryContainer = ColorProvider(PrimaryDarken2),
        error = ColorProvider(ErrorDefault),
        onError = ColorProvider(Gray1),
        errorContainer = ColorProvider(ErrorLighten2),
        onErrorContainer = ColorProvider(ErrorDarken2),
        background = ColorProvider(Gray2),
        onBackground = ColorProvider(Gray10),
        backgroundVariant = ColorProvider(Gray7),
        onBackgroundVariant = ColorProvider(Gray3),
        surface = ColorProvider(Gray1),
        onSurface = ColorProvider(Gray9),
        surfaceVariant = ColorProvider(Gray6),
        onSurfaceVariant = ColorProvider(Gray5),
        icon = ColorProvider(Gray5),
        line = ColorProvider(Gray3),
    )
}

internal val LocalColorProviders = staticCompositionLocalOf { dynamicThemeColorProviders() }
