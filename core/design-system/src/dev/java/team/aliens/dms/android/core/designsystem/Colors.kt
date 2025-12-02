package team.aliens.dms.android.core.designsystem

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
class Colors(
    primary: Color,
    onPrimary: Color,
    primaryContainer: Color,
    onPrimaryContainer: Color,
    error: Color,
    onError: Color,
    errorContainer: Color,
    onErrorContainer: Color,
    background: Color,
    onBackground: Color,
    backgroundVariant: Color,
    onBackgroundVariant: Color,
    surface: Color,
    onSurface: Color,
    surfaceVariant: Color,
    onSurfaceVariant: Color,
    icon: Color,
    line: Color,
    isLight: Boolean,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
        internal set
    var onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
        internal set

    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var errorContainer by mutableStateOf(errorContainer, structuralEqualityPolicy())
        internal set
    var onErrorContainer by mutableStateOf(onErrorContainer, structuralEqualityPolicy())
        internal set

    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var onBackground by mutableStateOf(onBackground, structuralEqualityPolicy())
        internal set
    var backgroundVariant by mutableStateOf(backgroundVariant, structuralEqualityPolicy())
        internal set
    var onBackgroundVariant by mutableStateOf(onBackgroundVariant, structuralEqualityPolicy())
        internal set

    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var onSurface by mutableStateOf(onSurface, structuralEqualityPolicy())
        internal set
    var surfaceVariant by mutableStateOf(surfaceVariant, structuralEqualityPolicy())
        internal set
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant, structuralEqualityPolicy())
        internal set

    var icon by mutableStateOf(icon, structuralEqualityPolicy())
        internal set
    var line by mutableStateOf(line, structuralEqualityPolicy())
        internal set

    var isLight by mutableStateOf(isLight, structuralEqualityPolicy())
        internal set

    fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        primaryContainer: Color = this.primaryContainer,
        onPrimaryContainer: Color = this.onPrimaryContainer,
        error: Color = this.error,
        onError: Color = this.onError,
        errorContainer: Color = this.errorContainer,
        onErrorContainer: Color = this.onErrorContainer,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        backgroundVariant: Color = this.backgroundVariant,
        onBackgroundVariant: Color = this.onBackgroundVariant,
        surface: Color = this.surface,
        onSurface: Color = this.onSurface,
        surfaceVariant: Color = this.surfaceVariant,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        icon: Color = this.icon,
        line: Color = this.line,
        isLight: Boolean = this.isLight,
    ): Colors = Colors(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        background = background,
        onBackground = onBackground,
        backgroundVariant = backgroundVariant,
        onBackgroundVariant = onBackgroundVariant,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        icon = icon,
        line = line,
        isLight = isLight,
    )

    override fun toString(): String = "Colors(" +
        "primary=$primary, " +
        "onPrimary=$onPrimary, " +
        "primaryContainer=$primaryContainer, " +
        "onPrimaryContainer=$onPrimaryContainer, " +
        "error=$error, " +
        "onError=$onError, " +
        "errorContainer=$errorContainer, " +
        "onErrorContainer=$onErrorContainer, " +
        "background=$background, " +
        "onBackground=$onBackground, " +
        "backgroundVariant=$backgroundVariant, " +
        "onBackgroundVariant=$onBackgroundVariant, " +
        "surface=$surface, " +
        "onSurface=$onSurface, " +
        "surfaceVariant=$surfaceVariant, " +
        "onSurfaceVariant=$onSurfaceVariant, " +
        ")"
}

internal sealed class DmsColor(
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray900: Color,
    val background: Color,
    val black: Color,
    val red50: Color,
    val red100: Color,
    val red200: Color,
    val red300: Color,
    val red400: Color,
    val red500: Color,
    val blue50: Color,
    val blue100: Color,
    val blue200: Color,
    val blue300: Color,
    val blue400: Color,
    val blue500: Color,
    val button: Color,
    val container: Color,
    val hover: Color,
    val pressed: Color,
) {
    data object Light : DmsColor(
        gray50 = Color(0xFFFFFFFF),
        gray100 = Color(0xFFF9F9F9),
        gray200 = Color(0xFFEEEEEE),
        gray300 = Color(0xFFDDDDDD),
        gray400 = Color(0xFF999999),
        gray500 = Color(0xFF555555),
        gray600 = Color(0xFF343434),
        gray700 = Color(0xFF202020),
        gray800 = Color(0xFF121212),
        gray900 = Color(0xFF101010),
        background = Color(0xFFF2F4F6),
        black = Color(0xFF121212),
        red50 = Color(0xFFFFE7E7),
        red100 = Color(0xFFFEB1B1),
        red200 = Color(0xFFFE6565),
        red300 = Color(0xFFFE0F0F),
        red400 = Color(0xFFCB0C0C),
        red500 = Color(0xFF530505),
        blue50 = Color(0xFFE7F0FF),
        blue100 = Color(0xFFB1D0FE),
        blue200 = Color(0xFF65A2FE),
        blue300 = Color(0xFF0F6EFE),
        blue400 = Color(0xFF0C58CB),
        blue500 = Color(0xFF052453),
        button = Color(0xFFB0B6C1),
        container = Color(0xFFFFFFFF),
        hover = Color(0xFF8D929A),
        pressed = Color(0xFF71757B),
    )

    data object Dark : DmsColor(
        gray50 = Color(0xFF101010),
        gray100 = Color(0xFF121212),
        gray200 = Color(0xFF202020),
        gray300 = Color(0xFF343434),
        gray400 = Color(0xFF555555),
        gray500 = Color(0xFF999999),
        gray600 = Color(0xFFDDDDDD),
        gray700 = Color(0xFFEEEEEE),
        gray800 = Color(0xFFF9F9F9),
        gray900 = Color(0xFFFFFFFF),
        background = Color(0xFF101010),
        black = Color(0xFFFFFFFF),
        red50 = Color(0xFF530505),
        red100 = Color(0xFFCB0C0C),
        red200 = Color(0xFFFE0F0F),
        red300 = Color(0xFFFE0F0F),
        red400 = Color(0xFFFEB1B1),
        red500 = Color(0xFFFFE7E7),
        blue50 = Color(0xFF052453),
        blue100 = Color(0xFF0C58CB),
        blue200 = Color(0xFF0F6EFE),
        blue300 = Color(0xFF0F6EFE),
        blue400 = Color(0xFFB1D0FE),
        blue500 = Color(0xFFE7F0FF),
        button = Color(0xFF4D5259),
        container = Color(0xFF171717),
        hover = Color(0xFF3E4247),
        pressed = Color(0xFF323539),
    )
}

fun lightColors(
    primary: Color = DmsColor.Light.blue400,
    onPrimary: Color = DmsColor.Light.gray50,
    primaryContainer: Color = DmsColor.Light.blue100,
    onPrimaryContainer: Color = DmsColor.Light.blue500,
    error: Color = DmsColor.Light.red300,
    onError: Color = DmsColor.Light.gray50,
    errorContainer: Color = DmsColor.Light.red50,
    onErrorContainer: Color = DmsColor.Light.red500,
    background: Color = DmsColor.Light.background,
    onBackground: Color = DmsColor.Light.gray900,
    backgroundVariant: Color = DmsColor.Light.gray700,
    onBackgroundVariant: Color = DmsColor.Light.gray200,
    surface: Color = DmsColor.Light.gray100,
    onSurface: Color = DmsColor.Light.gray900,
    surfaceVariant: Color = DmsColor.Light.gray200,
    onSurfaceVariant: Color = DmsColor.Light.gray700,
    icon: Color = DmsColor.Light.gray600,
    line: Color = DmsColor.Light.gray300,
): Colors = Colors(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    background = background,
    onBackground = onBackground,
    backgroundVariant = backgroundVariant,
    onBackgroundVariant = onBackgroundVariant,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    icon = icon,
    line = line,
    isLight = true,
)

fun darkColors(
    primary: Color = DmsColor.Dark.blue200,
    onPrimary: Color = DmsColor.Dark.gray900,
    primaryContainer: Color = DmsColor.Dark.blue300,
    onPrimaryContainer: Color = DmsColor.Dark.gray50,
    error: Color = DmsColor.Dark.red200,
    onError: Color = DmsColor.Dark.gray900,
    errorContainer: Color = DmsColor.Dark.red300,
    onErrorContainer: Color = DmsColor.Dark.gray50,
    background: Color = DmsColor.Dark.gray900,
    onBackground: Color = DmsColor.Dark.gray100,
    backgroundVariant: Color = DmsColor.Dark.gray700,
    onBackgroundVariant: Color = DmsColor.Dark.gray200,
    surface: Color = DmsColor.Dark.gray800,
    onSurface: Color = DmsColor.Dark.gray100,
    surfaceVariant: Color = DmsColor.Dark.gray700,
    onSurfaceVariant: Color = DmsColor.Dark.gray200,
    icon: Color = DmsColor.Dark.gray500,
    line: Color = DmsColor.Dark.gray400,
): Colors = Colors(
    primary = primary,
    onPrimary = onPrimary,
    primaryContainer = primaryContainer,
    onPrimaryContainer = onPrimaryContainer,
    error = error,
    onError = onError,
    errorContainer = errorContainer,
    onErrorContainer = onErrorContainer,
    background = background,
    onBackground = onBackground,
    backgroundVariant = backgroundVariant,
    onBackgroundVariant = onBackgroundVariant,
    surface = surface,
    onSurface = onSurface,
    surfaceVariant = surfaceVariant,
    onSurfaceVariant = onSurfaceVariant,
    icon = icon,
    line = line,
    isLight = false,
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }
