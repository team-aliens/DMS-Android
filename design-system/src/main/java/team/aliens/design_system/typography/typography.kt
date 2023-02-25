@file:Suppress("MemberVisibilityCanBePrivate")

package team.aliens.design_system.typography

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
import team.aliens.design_system.R
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable

val NotoSansFamily = FontFamily(
    Font(R.font.noto_sans_kr_black, FontWeight.Black),
    Font(R.font.noto_sans_kr_bold, FontWeight.Bold),
    Font(R.font.noto_sans_kr_medium, FontWeight.Medium),
    Font(R.font.noto_sans_kr_regular, FontWeight.Normal),
    Font(R.font.noto_sans_kr_light, FontWeight.Light),
    Font(R.font.noto_sans_kr_thin, FontWeight.Thin),
)

object DormTypography {

    @Stable
    val headline1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 50.sp,
    )

    @Stable
    val headline2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        lineHeight = 42.sp,
    )

    @Stable
    val headline3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 34.sp,
    )

    @Stable
    val title1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 34.sp,
    )

    @Stable
    val title2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 32.sp,
    )

    @Stable
    val title3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
    )

    // TODO subtitle 1 && subtitle 2 -> 삭제하고 사용중인것들 -> 다른 typography 로 교체 필요
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
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 25.sp,
    )

    @Stable
    val body1 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    )

    @Stable
    val body2 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )

    @Stable
    val body3 = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    )

    // TODO body 4 && body 5 -> 대체 필요
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
        lineHeight = 16.sp,
    )

    @Stable
    val button = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    )

    @Stable
    val roomDescription = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    )

    @Stable
    val bottomNavItemLabel = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )
}

@Composable
@NonRestartableComposable
fun Headline1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.headline1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Headline2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
    rippleEnabled: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.headline2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Headline3(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.headline3,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.title1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.title2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Title3(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.title3,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun SubTitle1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.subtitle1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun SubTitle2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.subtitle2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.body1,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.body2,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body3(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.body3,
        color = color,
    )
}
// TODO body 4 && body 5 -> 대체
@Composable
@NonRestartableComposable
fun Body4(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.body4,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun Body5(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.body5,
        color = color,
        lineHeight = 24.sp,
    )
}
// TODO onClick 삭제하고 loginScreen 에서 modifier 통해 클릭 이벤트 넣어주기
@Composable
@NonRestartableComposable
fun Caption(
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
        style = DormTypography.caption,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun OverLine(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.overline,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = DormColor.Gray900,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.button,
        color = color,
    )
}

@Composable
@NonRestartableComposable
fun BottomNavItemLabel(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
){
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = DormTypography.bottomNavItemLabel,
    )
}
