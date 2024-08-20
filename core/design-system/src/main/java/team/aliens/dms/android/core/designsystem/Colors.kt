package team.aliens.dms.android.core.designsystem

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
val PrimaryDefault = Color(0xff3d8aff)

@Stable
val PrimaryLighten1 = Color(0xffb1d0ff)

@Stable
val PrimaryLighten2 = Color(0xFFE6F0FF)

@Stable
val PrimaryDarken1 = Color(0xff1070ff)

@Stable
val PrimaryDarken2 = Color(0xff005de8)

@Stable
val ErrorDefault = Color(0xffff4646)

@Stable
val ErrorLighten1 = Color(0xffffd3d3)

@Stable
val ErrorLighten2 = Color(0xfffff0f0)

@Stable
val ErrorDarken1 = Color(0xffc23535)

@Stable
val ErrorDarken2 = Color(0xffc23535)

@Stable
val Gray1 = Color(0xffffffff)

@Stable
val Gray2 = Color(0xfff9f9f9)

@Stable
val Gray3 = Color(0xffeeeeee)

@Stable
val Gray4 = Color(0xffdddddd)

@Stable
val Gray5 = Color(0xff999999)

@Stable
val Gray6 = Color(0xff555555)

@Stable
val Gray7 = Color(0xff343434)

@Stable
val Gray8 = Color(0xff202020)

@Stable
val Gray9 = Color(0xff121212)

@Stable
val Gray10 = Color(0xff000000)

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
        errorContainer: Color = this.onErrorContainer,
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

fun lightColors(
    primary: Color = PrimaryDefault,
    onPrimary: Color = Gray1,
    primaryContainer: Color = PrimaryLighten2,
    onPrimaryContainer: Color = PrimaryDarken2,
    error: Color = ErrorDefault,
    onError: Color = Gray1,
    errorContainer: Color = ErrorLighten2,
    onErrorContainer: Color = ErrorDarken2,
    background: Color = Gray2,
    onBackground: Color = Gray10,
    backgroundVariant: Color = Gray7,
    onBackgroundVariant: Color = Gray3,
    surface: Color = Gray1,
    onSurface: Color = Gray9,
    surfaceVariant: Color = Gray6,
    onSurfaceVariant: Color = Gray5,
    icon: Color = Gray5,
    line: Color = Gray3,
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
    primary: Color = PrimaryDefault,
    onPrimary: Color = Gray1,
    primaryContainer: Color = PrimaryLighten1,
    onPrimaryContainer: Color = PrimaryDarken2,
    error: Color = ErrorDefault,
    onError: Color = Gray1,
    errorContainer: Color = ErrorLighten1,
    onErrorContainer: Color = ErrorDarken2,
    background: Color = Gray9,
    onBackground: Color = Gray1,
    backgroundVariant: Color = Gray3,
    onBackgroundVariant: Color = Gray7,
    surface: Color = Gray8,
    onSurface: Color = Gray2,
    surfaceVariant: Color = Gray4,
    onSurfaceVariant: Color = Gray5,
    icon: Color = Gray5,
    line: Color = Gray7,
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
