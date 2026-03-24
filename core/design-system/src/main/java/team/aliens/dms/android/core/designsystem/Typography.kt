package team.aliens.dms.android.core.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
class Typography(
    val headline1: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.2.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val headline2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 38.2.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val headline3: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 33.4.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val title1: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 33.4.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val title2: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.6.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val title3: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.6.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val body1: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 23.9.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val body2: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 23.9.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val body3: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 19.1.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val caption: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.1.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val overline: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.3.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
    val button: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 14.3.sp,
    ).withDefaultFontFamily(pretendardFontFamily),
) {
    fun copy(
        headline1: TextStyle = this.headline1,
        headline2: TextStyle = this.headline2,
        headline3: TextStyle = this.headline3,
        title1: TextStyle = this.title1,
        title2: TextStyle = this.title2,
        title3: TextStyle = this.title3,
        body1: TextStyle = this.body1,
        body2: TextStyle = this.body2,
        body3: TextStyle = this.body3,
        caption: TextStyle = this.caption,
        overline: TextStyle = this.overline,
        button: TextStyle = this.button,
    ): Typography = Typography(
        headline1 = headline1,
        headline2 = headline2,
        headline3 = headline3,
        title1 = title1,
        title2 = title2,
        title3 = title3,
        body1 = body1,
        body2 = body2,
        body3 = body3,
        caption = caption,
        overline = overline,
        button = button,
    )

    override fun equals(other: Any?): Boolean =
        this === other ||
            (
                other is Typography &&
                    headline1 == other.headline1 &&
                    headline2 == other.headline2 &&
                    headline3 == other.headline3 &&
                    title1 == other.title1 &&
                    title2 == other.title2 &&
                    title3 == other.title3 &&
                    body1 == other.body1 &&
                    body2 == other.body2 &&
                    body3 == other.body3 &&
                    caption == other.caption &&
                    overline == other.overline &&
                    button == other.button
                )

    override fun hashCode(): Int {
        var result = headline1.hashCode()
        result = 31 * result + headline2.hashCode()
        result = 31 * result + headline3.hashCode()
        result = 31 * result + title1.hashCode()
        result = 31 * result + title2.hashCode()
        result = 31 * result + title3.hashCode()
        result = 31 * result + body1.hashCode()
        result = 31 * result + body2.hashCode()
        result = 31 * result + body3.hashCode()
        result = 31 * result + caption.hashCode()
        result = 31 * result + overline.hashCode()
        result = 31 * result + button.hashCode()
        return result
    }

    override fun toString(): String {
        return "Typography(headline1=$headline1, " +
            "headline2=$headline2, " +
            "headline3=$headline3, " +
            "title1=$title1, " +
            "title2=$title2, " +
            "title3=$title3, " +
            "body1=$body1, " +
            "body2=$body2, " +
            "body3=$body3, " +
            "caption=$caption, " +
            "overline=$overline, " +
            "button=$button" +
            ")"
    }
}

val pretendardFontFamily: FontFamily = FontFamily(
    Font(
        resId = R.font.pretendard_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.pretendard_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.pretendard_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.pretendard_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
    ),
)

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle =
    if (fontFamily != null) {
        this
    } else {
        this.copy(fontFamily = default)
    }

val Typography.headlineB: TextStyle get() = headline1
val Typography.headlineM: TextStyle get() = headline2
val Typography.titleB: TextStyle get() = headline3
val Typography.titleM: TextStyle get() = title1
val Typography.sTitleB: TextStyle get() = title2
val Typography.sTitleM: TextStyle get() = title3
val Typography.lBodyB: TextStyle get() = body1
val Typography.lBodyM: TextStyle get() = body2
val Typography.bodyB: TextStyle get() = body3
val Typography.bodyM: TextStyle get() = caption
val Typography.labelB: TextStyle get() = overline
val Typography.labelM: TextStyle get() = button

val Typography.sLabelB: TextStyle
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 11.9.sp,
    )

val Typography.sLabelM: TextStyle
    get() = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 11.9.sp,
    )

internal val LocalTypography = staticCompositionLocalOf { Typography() }
