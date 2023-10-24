package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
class Shapes(
    val extraSmall: CornerBasedShape = RoundedCornerShape(2.dp),
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
    val medium: CornerBasedShape = RoundedCornerShape(8.dp),
    val large: CornerBasedShape = RoundedCornerShape(12.dp),
    val extraLarge: CornerBasedShape = RoundedCornerShape(16.dp),
    val circle: CornerBasedShape = CircleShape,
    val surface: CornerBasedShape = RoundedCornerShape(24.dp),
) {
    fun copy(
        extraSmall: CornerBasedShape = this.small,
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large,
        extraLarge: CornerBasedShape = this.large,
        circle: CornerBasedShape = this.circle,
        surface: CornerBasedShape = this.surface,
    ): Shapes = Shapes(
        extraSmall = extraSmall,
        small = small,
        medium = medium,
        large = large,
        extraLarge = extraLarge,
        circle = circle,
        surface = surface,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Shapes) {
            return false
        }

        if (extraSmall != other.extraSmall) {
            return false
        }
        if (small != other.small) {
            return false
        }
        if (medium != other.medium) {
            return false
        }
        if (large != other.large) {
            return false
        }
        if (extraLarge != other.extraLarge) {
            return false
        }
        if (circle != other.circle) {
            return false
        }
        if (surface != other.surface) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = extraSmall.hashCode()
        result = 31 * result + small.hashCode()
        result = 31 * result + medium.hashCode()
        result = 31 * result + large.hashCode()
        result = 31 * result + extraLarge.hashCode()
        result = 31 * result + circle.hashCode()
        result = 31 * result + surface.hashCode()
        return result
    }

    override fun toString(): String {
        return "Shapes(small=$small, medium=$medium, large=$large)"
    }
}

internal val LocalShapes = staticCompositionLocalOf { Shapes() }
