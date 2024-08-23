package team.aliens.dms.android.core.widget.designsystem

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.glance.material3.ColorProviders
import team.aliens.dms.android.core.designsystem.ErrorDarken2
import team.aliens.dms.android.core.designsystem.ErrorDefault
import team.aliens.dms.android.core.designsystem.ErrorLighten1
import team.aliens.dms.android.core.designsystem.ErrorLighten2
import team.aliens.dms.android.core.designsystem.Gray1
import team.aliens.dms.android.core.designsystem.Gray10
import team.aliens.dms.android.core.designsystem.Gray2
import team.aliens.dms.android.core.designsystem.Gray3
import team.aliens.dms.android.core.designsystem.Gray4
import team.aliens.dms.android.core.designsystem.Gray5
import team.aliens.dms.android.core.designsystem.Gray6
import team.aliens.dms.android.core.designsystem.Gray7
import team.aliens.dms.android.core.designsystem.Gray8
import team.aliens.dms.android.core.designsystem.Gray9
import team.aliens.dms.android.core.designsystem.PrimaryDarken2
import team.aliens.dms.android.core.designsystem.PrimaryDefault
import team.aliens.dms.android.core.designsystem.PrimaryLighten1
import team.aliens.dms.android.core.designsystem.PrimaryLighten2

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDefault,
    onPrimary = Gray1,
    primaryContainer = PrimaryLighten2,
    onPrimaryContainer = PrimaryDarken2,
    error = ErrorDefault,
    onError = Gray1,
    errorContainer = ErrorLighten2,
    onErrorContainer = ErrorDarken2,
    background = Gray2,
    onBackground = Gray10,
    inverseSurface = Gray7,
    inverseOnSurface = Gray3,
    surface = Gray1,
    onSurface = Gray9,
    surfaceVariant = Gray6,
    onSurfaceVariant = Gray5,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDefault,
    onPrimary = Gray1,
    primaryContainer = PrimaryLighten1,
    onPrimaryContainer = PrimaryDarken2,
    error = ErrorDefault,
    onError = Gray1,
    errorContainer = ErrorLighten1,
    onErrorContainer = ErrorDarken2,
    background = Gray9,
    onBackground = Gray1,
    inverseSurface = Gray3,
    inverseOnSurface = Gray7,
    surface = Gray8,
    onSurface = Gray2,
    surfaceVariant = Gray4,
    onSurfaceVariant = Gray5,
)

object DmsWidgetGlanceColorScheme {
    val colors = ColorProviders(
        light = LightColorScheme,
        dark = DarkColorScheme,
    )
}
