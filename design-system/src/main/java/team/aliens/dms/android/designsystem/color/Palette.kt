package team.aliens.dms.android.designsystem.color

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
}
