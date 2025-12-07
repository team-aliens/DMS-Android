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

private val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = team.aliens.dms.android.core.designsystem.PrimaryDefault,
    onPrimary = team.aliens.dms.android.core.designsystem.Gray1,
    primaryContainer = team.aliens.dms.android.core.designsystem.PrimaryLighten2,
    onPrimaryContainer = team.aliens.dms.android.core.designsystem.PrimaryDarken2,
    error = team.aliens.dms.android.core.designsystem.ErrorDefault,
    onError = team.aliens.dms.android.core.designsystem.Gray1,
    errorContainer = team.aliens.dms.android.core.designsystem.ErrorLighten2,
    onErrorContainer = team.aliens.dms.android.core.designsystem.ErrorDarken2,
    background = team.aliens.dms.android.core.designsystem.Gray2,
    onBackground = team.aliens.dms.android.core.designsystem.Gray10,
    inverseSurface = team.aliens.dms.android.core.designsystem.Gray7,
    inverseOnSurface = team.aliens.dms.android.core.designsystem.Gray3,
    surface = team.aliens.dms.android.core.designsystem.Gray1,
    onSurface = team.aliens.dms.android.core.designsystem.Gray9,
    surfaceVariant = team.aliens.dms.android.core.designsystem.Gray6,
    onSurfaceVariant = team.aliens.dms.android.core.designsystem.Gray5,
)

private val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = team.aliens.dms.android.core.designsystem.PrimaryDefault,
    onPrimary = team.aliens.dms.android.core.designsystem.Gray1,
    primaryContainer = team.aliens.dms.android.core.designsystem.PrimaryLighten1,
    onPrimaryContainer = team.aliens.dms.android.core.designsystem.PrimaryDarken2,
    error = team.aliens.dms.android.core.designsystem.ErrorDefault,
    onError = team.aliens.dms.android.core.designsystem.Gray1,
    errorContainer = team.aliens.dms.android.core.designsystem.ErrorLighten1,
    onErrorContainer = team.aliens.dms.android.core.designsystem.ErrorDarken2,
    background = team.aliens.dms.android.core.designsystem.Gray9,
    onBackground = team.aliens.dms.android.core.designsystem.Gray1,
    inverseSurface = team.aliens.dms.android.core.designsystem.Gray3,
    inverseOnSurface = team.aliens.dms.android.core.designsystem.Gray7,
    surface = team.aliens.dms.android.core.designsystem.Gray8,
    onSurface = team.aliens.dms.android.core.designsystem.Gray2,
    surfaceVariant = team.aliens.dms.android.core.designsystem.Gray4,
    onSurfaceVariant = team.aliens.dms.android.core.designsystem.Gray5,
)

object DmsWidgetGlanceColorScheme {
    val colors = androidx.glance.material3.ColorProviders(
        light = LightColorScheme,
        dark = DarkColorScheme,
    )
}
