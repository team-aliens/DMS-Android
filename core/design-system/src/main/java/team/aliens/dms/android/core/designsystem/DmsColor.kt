package team.aliens.dms.android.core.designsystem

import androidx.compose.ui.graphics.Color

internal sealed class DmsColor {
    abstract val gray50: Color
    abstract val gray100: Color
    abstract val gray200: Color
    abstract val gray300: Color
    abstract val gray400: Color
    abstract val gray500: Color
    abstract val gray600: Color
    abstract val gray700: Color
    abstract val gray800: Color
    abstract val gray900: Color
    abstract val background: Color
    abstract val black: Color
    abstract val red50: Color
    abstract val red100: Color
    abstract val red200: Color
    abstract val red300: Color
    abstract val red400: Color
    abstract val red500: Color
    abstract val blue50: Color
    abstract val blue100: Color
    abstract val blue200: Color
    abstract val blue300: Color
    abstract val blue400: Color
    abstract val blue500: Color
    abstract val button: Color
    abstract val container: Color
    abstract val hover: Color
    abstract val pressed: Color

    data object Light : DmsColor() {
        override val gray50: Color = Color(0xFFFFFFFF)
        override val gray100: Color = Color(0xFFF9F9F9)
        override val gray200: Color = Color(0xFFEEEEEE)
        override val gray300: Color = Color(0xFFDDDDDD)
        override val gray400: Color = Color(0xFF999999)
        override val gray500: Color = Color(0xFF555555)
        override val gray600: Color = Color(0xFF343434)
        override val gray700: Color = Color(0xFF202020)
        override val gray800: Color = Color(0xFF121212)
        override val gray900: Color = Color(0xFF101010)
        override val background: Color = Color(0xFFF2F4F6)
        override val black: Color = Color(0xFF121212)
        override val red50: Color = Color(0xFFFFE7E7)
        override val red100: Color = Color(0xFFFEB1B1)
        override val red200: Color = Color(0xFFFE6565)
        override val red300: Color = Color(0xFFFE0F0F)
        override val red400: Color = Color(0xFFCB0C0C)
        override val red500: Color = Color(0xFF530505)
        override val blue50: Color = Color(0xFFE7F0FF)
        override val blue100: Color = Color(0xFFB1D0FE)
        override val blue200: Color = Color(0xFF65A2FE)
        override val blue300: Color = Color(0xFF0F6EFE)
        override val blue400: Color = Color(0xFF0C58CB)
        override val blue500: Color = Color(0xFF052453)
        override val button: Color = Color(0xFFB0B6C1)
        override val container: Color = Color(0xFFFFFFFF)
        override val hover: Color = Color(0xFF8D929A)
        override val pressed: Color = Color(0xFF71757B)
    }

    data object Dark : DmsColor() {
        override val gray50: Color = Color(0xFF101010)
        override val gray100: Color = Color(0xFF121212)
        override val gray200: Color = Color(0xFF202020)
        override val gray300: Color = Color(0xFF343434)
        override val gray400: Color = Color(0xFF555555)
        override val gray500: Color = Color(0xFF999999)
        override val gray600: Color = Color(0xFFDDDDDD)
        override val gray700: Color = Color(0xFFEEEEEE)
        override val gray800: Color = Color(0xFFF9F9F9)
        override val gray900: Color = Color(0xFFFFFFFF)
        override val background: Color = Color(0xFF101010)
        override val black: Color = Color(0xFFFFFFFF)
        override val red50: Color = Color(0xFF530505)
        override val red100: Color = Color(0xFFCB0C0C)
        override val red200: Color = Color(0xFFFE0F0F)
        override val red300: Color = Color(0xFFFE0F0F)
        override val red400: Color = Color(0xFFFEB1B1)
        override val red500: Color = Color(0xFFFFE7E7)
        override val blue50: Color = Color(0xFF052453)
        override val blue100: Color = Color(0xFF0C58CB)
        override val blue200: Color = Color(0xFF0F6EFE)
        override val blue300: Color = Color(0xFF0F6EFE)
        override val blue400: Color = Color(0xFFB1D0FE)
        override val blue500: Color = Color(0xFFE7F0FF)
        override val button: Color = Color(0xFF4D5259)
        override val container: Color = Color(0xFF171717)
        override val hover: Color = Color(0xFF3E4247)
        override val pressed: Color = Color(0xFF323539)
    }
}
