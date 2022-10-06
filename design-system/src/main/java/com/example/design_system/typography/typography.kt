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
import androidx.compose.ui.unit.dp
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
    val title1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
    )

    @Stable
    val title2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
    )

    @Stable
    val title3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    )

    @Stable
    val title4 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    )


    @Stable
    val title5 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
    )

    @Stable
    val body1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    )

    @Stable
    val body2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    )

    @Stable
    val body3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
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
    val body6 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )

    @Stable
    val body7 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    )

    @Stable
    val button = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    )
}

@Composable
@NonRestartableComposable
fun Title1(
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
        style = DormTypography.title1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title2(
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
        style = DormTypography.title2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title3(
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
        style = DormTypography.title3,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title4(
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
        style = DormTypography.title4,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title5(
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
        style = DormTypography.title5,
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
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = Modifier.dormClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick
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
fun Body6(
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
        style = DormTypography.body6,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body7(
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
        style = DormTypography.body7,
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