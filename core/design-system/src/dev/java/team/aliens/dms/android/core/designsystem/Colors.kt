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
    inversePrimary: Color,
    secondary: Color,
    onSecondary: Color,
    secondaryContainer: Color,
    onSecondaryContainer: Color,
    tertiary: Color,
    onTertiary: Color,
    tertiaryContainer: Color,
    onTertiaryContainer: Color,
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
    surfaceTint: Color,
    inverseSurface: Color,
    inverseOnSurface: Color,
    outline: Color,
    outlineVariant: Color,
    tint: Color,
    divider: Color,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var primaryContainer by mutableStateOf(primaryContainer, structuralEqualityPolicy())
        internal set
    var onPrimaryContainer by mutableStateOf(onPrimaryContainer, structuralEqualityPolicy())
        internal set
    var inversePrimary by mutableStateOf(inversePrimary, structuralEqualityPolicy())
        internal set

    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var onSecondary by mutableStateOf(onSecondary, structuralEqualityPolicy())
        internal set
    var secondaryContainer by mutableStateOf(secondaryContainer, structuralEqualityPolicy())
        internal set
    var onSecondaryContainer by mutableStateOf(onSecondaryContainer, structuralEqualityPolicy())
        internal set

    var tertiary by mutableStateOf(tertiary, structuralEqualityPolicy())
        internal set
    var onTertiary by mutableStateOf(onTertiary, structuralEqualityPolicy())
        internal set
    var tertiaryContainer by mutableStateOf(tertiaryContainer, structuralEqualityPolicy())
        internal set
    var onTertiaryContainer by mutableStateOf(onTertiaryContainer, structuralEqualityPolicy())
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
    var surfaceTint by mutableStateOf(surfaceTint, structuralEqualityPolicy())
        internal set

    var inverseSurface by mutableStateOf(inverseSurface, structuralEqualityPolicy())
        internal set
    var inverseOnSurface by mutableStateOf(inverseOnSurface, structuralEqualityPolicy())
        internal set

    var outline by mutableStateOf(outline, structuralEqualityPolicy())
        internal set
    var outlineVariant by mutableStateOf(outlineVariant, structuralEqualityPolicy())
        internal set

    var icon by mutableStateOf(tint, structuralEqualityPolicy())
        internal set
    var line by mutableStateOf(divider, structuralEqualityPolicy())
        internal set

    fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        primaryContainer: Color = this.primaryContainer,
        onPrimaryContainer: Color = this.onPrimaryContainer,
        inversePrimary: Color = this.inversePrimary,
        secondary: Color = this.secondary,
        onSecondary: Color = this.onSecondary,
        secondaryContainer: Color = this.secondaryContainer,
        onSecondaryContainer: Color = this.onSecondaryContainer,
        tertiary: Color = this.tertiary,
        onTertiary: Color = this.onTertiary,
        tertiaryContainer: Color = this.tertiaryContainer,
        onTertiaryContainer: Color = this.onTertiaryContainer,
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
        surfaceTint: Color = this.surfaceTint,
        inverseSurface: Color = this.inverseSurface,
        inverseOnSurface: Color = this.inverseOnSurface,
        outline: Color = this.outline,
        outlineVariant: Color = this.outlineVariant,
        icon: Color = this.icon,
        line: Color = this.line,
    ): Colors = Colors(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = inversePrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
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
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        outline = outline,
        outlineVariant = outlineVariant,
        tint = icon,
        divider = line,
    )

    override fun toString(): String = "Colors(" +
        "primary=$primary, " +
        "onPrimary=$onPrimary, " +
        "primaryContainer=$primaryContainer, " +
        "onPrimaryContainer=$onPrimaryContainer, " +
        "inversePrimary=$inversePrimary, " +
        "secondary=$secondary, " +
        "onSecondary=$onSecondary, " +
        "secondaryContainer=$secondaryContainer, " +
        "onSecondaryContainer=$onSecondaryContainer, " +
        "tertiary=$tertiary, " +
        "onTertiary=$onTertiary, " +
        "tertiaryContainer=$tertiaryContainer, " +
        "onTertiaryContainer=$onTertiaryContainer, " +
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
        "surfaceTint=$surfaceTint, " +
        "inverseSurface=$inverseSurface, " +
        "inverseOnSurface=$inverseOnSurface, " +
        "outline=$outline, " +
        "outlineVariant=$outlineVariant" +
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
    primaryColor: Color = DmsColor.Light.blue50,
    onPrimaryColor: Color = DmsColor.Light.blue100,
    primaryContainerColor: Color = DmsColor.Light.blue200,
    onPrimaryContainerColor: Color = DmsColor.Light.blue300,
    inversePrimaryColor: Color = DmsColor.Light.blue400,
    secondaryColor: Color = DmsColor.Light.blue500,
    surfaceColor: Color = DmsColor.Light.gray50,
    onSurfaceColor: Color = DmsColor.Light.gray100,
    surfaceVariantColor: Color = DmsColor.Light.gray200,
    onSurfaceVariantColor: Color = DmsColor.Light.gray300,
    inverseSurfaceColor: Color = DmsColor.Light.gray400,
    inverseOnSurfaceColor: Color = DmsColor.Light.gray500,
    tertiaryContainerColor: Color = DmsColor.Light.gray600,
    onTertiaryContainerColor: Color = DmsColor.Light.gray700,
    surfaceBrightColor: Color = DmsColor.Light.gray800,
    surfaceContainerColor: Color = DmsColor.Light.gray900,
    errorColor: Color = DmsColor.Light.red50,
    onErrorColor: Color = DmsColor.Light.red100,
    errorContainerColor: Color = DmsColor.Light.red200,
    onErrorContainerColor: Color = DmsColor.Light.red300,
    outlineColor: Color = DmsColor.Light.red400,
    outlineVariantColor: Color = DmsColor.Light.red500,
    backgroundColor: Color = DmsColor.Light.background,
    onBackgroundColor: Color = DmsColor.Light.black,
    surfaceTintColor: Color = DmsColor.Light.container,
    scrimColor: Color = DmsColor.Light.button,
    tertiaryColor: Color = DmsColor.Light.hover,
    onTertiaryColor: Color = DmsColor.Light.pressed,
): Colors = Colors(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
    primaryContainer = primaryContainerColor,
    onPrimaryContainer = onPrimaryContainerColor,
    inversePrimary = inversePrimaryColor,
    secondary = secondaryColor,
    onSecondary = surfaceBrightColor,
    secondaryContainer = surfaceContainerColor,
    onSecondaryContainer = surfaceContainerColor,
    tertiary = tertiaryColor,
    onTertiary = onTertiaryColor,
    tertiaryContainer = tertiaryContainerColor,
    onTertiaryContainer = onTertiaryContainerColor,
    error = errorColor,
    onError = onErrorColor,
    errorContainer = errorContainerColor,
    onErrorContainer = onErrorContainerColor,
    background = backgroundColor,
    onBackground = onBackgroundColor,
    backgroundVariant = backgroundColor,
    onBackgroundVariant = onBackgroundColor,
    surface = surfaceColor,
    onSurface = onSurfaceColor,
    surfaceVariant = surfaceVariantColor,
    onSurfaceVariant = onSurfaceVariantColor,
    surfaceTint = surfaceTintColor,
    inverseSurface = inverseSurfaceColor,
    inverseOnSurface = inverseOnSurfaceColor,
    outline = outlineColor,
    outlineVariant = outlineVariantColor,
    tint = scrimColor,
    divider = scrimColor,
)

fun darkColors(
    primaryColor: Color = DmsColor.Dark.blue50,
    onPrimaryColor: Color = DmsColor.Dark.blue100,
    primaryContainerColor: Color = DmsColor.Dark.blue200,
    onPrimaryContainerColor: Color = DmsColor.Dark.blue300,
    inversePrimaryColor: Color = DmsColor.Dark.blue400,
    secondaryColor: Color = DmsColor.Dark.blue500,
    surfaceColor: Color = DmsColor.Dark.gray50,
    onSurfaceColor: Color = DmsColor.Dark.gray100,
    surfaceVariantColor: Color = DmsColor.Dark.gray200,
    onSurfaceVariantColor: Color = DmsColor.Dark.gray300,
    inverseSurfaceColor: Color = DmsColor.Dark.gray400,
    inverseOnSurfaceColor: Color = DmsColor.Dark.gray500,
    tertiaryContainerColor: Color = DmsColor.Dark.gray600,
    onTertiaryContainerColor: Color = DmsColor.Dark.gray700,
    surfaceBrightColor: Color = DmsColor.Dark.gray800,
    surfaceContainerColor: Color = DmsColor.Dark.gray900,
    errorColor: Color = DmsColor.Dark.red50,
    onErrorColor: Color = DmsColor.Dark.red100,
    errorContainerColor: Color = DmsColor.Dark.red200,
    onErrorContainerColor: Color = DmsColor.Dark.red300,
    outlineColor: Color = DmsColor.Dark.red400,
    outlineVariantColor: Color = DmsColor.Dark.red500,
    backgroundColor: Color = DmsColor.Dark.background,
    onBackgroundColor: Color = DmsColor.Dark.black,
    surfaceTintColor: Color = DmsColor.Dark.container,
    scrimColor: Color = DmsColor.Dark.button,
    tertiaryColor: Color = DmsColor.Dark.hover,
    onTertiaryColor: Color = DmsColor.Dark.pressed,
): Colors = Colors(
    primary = primaryColor,
    onPrimary = onPrimaryColor,
    primaryContainer = primaryContainerColor,
    onPrimaryContainer = onPrimaryContainerColor,
    inversePrimary = inversePrimaryColor,
    secondary = secondaryColor,
    onSecondary = surfaceBrightColor,
    secondaryContainer = surfaceContainerColor,
    onSecondaryContainer = surfaceContainerColor,
    tertiary = tertiaryColor,
    onTertiary = onTertiaryColor,
    tertiaryContainer = tertiaryContainerColor,
    onTertiaryContainer = onTertiaryContainerColor,
    error = errorColor,
    onError = onErrorColor,
    errorContainer = errorContainerColor,
    onErrorContainer = onErrorContainerColor,
    background = backgroundColor,
    onBackground = onBackgroundColor,
    backgroundVariant = backgroundColor,
    onBackgroundVariant = onBackgroundColor,
    surface = surfaceColor,
    onSurface = onSurfaceColor,
    surfaceVariant = surfaceVariantColor,
    onSurfaceVariant = onSurfaceVariantColor,
    surfaceTint = surfaceTintColor,
    inverseSurface = inverseSurfaceColor,
    inverseOnSurface = inverseOnSurfaceColor,
    outline = outlineColor,
    outlineVariant = outlineVariantColor,
    tint = scrimColor,
    divider = scrimColor,
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }
