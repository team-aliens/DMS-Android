package team.aliens.dms.android.core.widget.designsystem

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.glance.material3.ColorProviders

private val PrimaryDefault = Color(0xFF0F6EFE)
private val PrimaryLighten1 = Color(0xFF65A2FE)
private val PrimaryLighten2 = Color(0xFFB1D0FE)
private val PrimaryDarken2 = Color(0xFF0C58CB)
private val ErrorDefault = Color(0xFFFE0F0F)
private val ErrorLighten1 = Color(0xFFFE6565)
private val ErrorLighten2 = Color(0xFFFEB1B1)
private val ErrorDarken2 = Color(0xFFCB0C0C)
private val Gray1 = Color(0xFFFFFFFF)
private val Gray2 = Color(0xFFF9F9F9)
private val Gray3 = Color(0xFFEEEEEE)
private val Gray4 = Color(0xFFDDDDDD)
private val Gray5 = Color(0xFF999999)
private val Gray6 = Color(0xFF555555)
private val Gray7 = Color(0xFF343434)
private val Gray8 = Color(0xFF202020)
private val Gray9 = Color(0xFF121212)
private val Gray10 = Color(0xFF101010)

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