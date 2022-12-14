@file:Suppress("MemberVisibilityCanBePrivate")

package com.example.design_system.typography

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable

val NotoSansFamily = FontFamily(
    Font(R.font.noto_sans_kr_black, FontWeight.Black),
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold),
    Font(R.font.noto_sans_kr_medium, FontWeight.Medium),
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal),
    Font(R.font.noto_sans_kr_light, FontWeight.Light),
    Font(R.font.noto_sans_kr_thin, FontWeight.Thin)
)

object DormTypography {

    @Stable
    val headline1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 49.sp,
    )

    @Stable
    val headline2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        lineHeight = 41.sp,
    )

    @Stable
    val headline3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 33.sp,
    )

    @Stable
    val subtitle1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 30.sp,
    )


    @Stable
    val subtitle2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 25.sp,
    )

    @Stable
    val body1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 30.sp,
    )

    @Stable
    val body2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 27.sp,
    )

    @Stable
    val body3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 32.sp,
    )

    @Stable
    val body4 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp,
    )

    @Stable
    val body5 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    )

    @Stable
    val caption = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 22.sp,
    )

    @Stable
    val overline = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 14.sp,
    )

    @Stable
    val button = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    )

    @Stable
    val roomDescription = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    )
}

@Composable
@NonRestartableComposable
fun Headline1(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.headline1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Headline2(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.headline2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Headline3(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.headline3,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun SubTitle1(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.subtitle1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun SubTitle2(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.subtitle2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body1(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.body1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body2(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.body2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body3(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.body3,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body4(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.body4,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body5(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.body5,
        color = color,
        lineHeight = 24.sp,
    )
}

@Composable
@NonRestartableComposable
fun Caption(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.caption,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun OverLine(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.overline,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun ButtonText(
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        text = text,
        style = DormTypography.button,
        color = color,
    )
}