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
class Typography internal constructor(
    val headline1: TextStyle,
    val headline2: TextStyle,
    val headline3: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val caption: TextStyle,
    val overline: TextStyle,
    val button: TextStyle,
) {
    constructor(
        headlineB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 38.2.sp
        ),
        headlineM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp,
            lineHeight = 38.2.sp,
        ),
        titleB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 33.4.sp,
        ),
        titleM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 33.4.sp,
        ),
        sTitleB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 28.6.sp,
        ),
        sTitleM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 28.6.sp,
        ),
        lBodyB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 23.9.sp,
        ),
        lBodyM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 23.9.sp,
        ),
        bodyB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 19.1.sp,
        ),
        bodyM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 19.1.sp,
        ),
        labelB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 14.3.sp,
        ),
        labelM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 14.3.sp,
        ),
        sLabelB: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 11.9.sp,
        ),
        sLabelM: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 11.9.sp,
        ),
    ) : this(
        headline1 = headlineB.withDefaultFontFamily(notoSansFontFamily),
        headline2 = headlineM.withDefaultFontFamily(notoSansFontFamily),
        headline3 = titleB.withDefaultFontFamily(notoSansFontFamily),
        title1 = titleM.withDefaultFontFamily(notoSansFontFamily),
        title2 = sTitleB.withDefaultFontFamily(notoSansFontFamily),
        title3 = sTitleM.withDefaultFontFamily(notoSansFontFamily),
        body1 = lBodyB.withDefaultFontFamily(notoSansFontFamily),
        body2 = lBodyM.withDefaultFontFamily(notoSansFontFamily),
        body3 = bodyB.withDefaultFontFamily(notoSansFontFamily),
        caption = bodyM.withDefaultFontFamily(notoSansFontFamily),
        overline = labelB.withDefaultFontFamily(notoSansFontFamily),
        button = labelM.withDefaultFontFamily(notoSansFontFamily),
    )

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Typography) return false

        if (headline1 != other.headline1) return false
        if (headline2 != other.headline2) return false
        if (headline3 != other.headline3) return false
        if (title1 != other.title1) return false
        if (title2 != other.title2) return false
        if (title3 != other.title3) return false
        if (body1 != other.body1) return false
        if (body2 != other.body2) return false
        if (body3 != other.body3) return false
        if (caption != other.caption) return false
        if (overline != other.overline) return false
        if (button != other.button) return false

        return true
    }

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

internal val notoSansFontFamily: FontFamily = FontFamily(
    Font(
        resId = R.font.noto_sans_kr_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.noto_sans_kr_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.noto_sans_kr_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.noto_sans_kr_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.noto_sans_kr_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.noto_sans_kr_thin,
        weight = FontWeight.Thin,
        style = FontStyle.Normal,
    ),
)

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle =
    if (fontFamily != null) {
        this
    } else {
        this.copy(fontFamily = default)
    }

// 새 디자인 시스템 속성명으로 접근하기 위한 확장 속성
inline val Typography.headlineB: TextStyle get() = headline1
inline val Typography.headlineM: TextStyle get() = headline2
inline val Typography.titleB: TextStyle get() = headline3
inline val Typography.titleM: TextStyle get() = title1
inline val Typography.sTitleB: TextStyle get() = title2
inline val Typography.sTitleM: TextStyle get() = title3
inline val Typography.lBodyB: TextStyle get() = body1
inline val Typography.lBodyM: TextStyle get() = body2
inline val Typography.bodyB: TextStyle get() = body3
inline val Typography.bodyM: TextStyle get() = caption
inline val Typography.labelB: TextStyle get() = overline
inline val Typography.labelM: TextStyle get() = button

// dev 전용 추가 속성
val Typography.sLabelB: TextStyle
    get() = TextStyle(
        fontFamily = notoSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 11.9.sp,
    )

val Typography.sLabelM: TextStyle
    get() = TextStyle(
        fontFamily = notoSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 11.9.sp,
    )

internal val LocalTypography = staticCompositionLocalOf { Typography() }
