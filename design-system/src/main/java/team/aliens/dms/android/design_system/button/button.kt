package team.aliens.dms.android.design_system.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.design_system.annotation.DormDeprecated
import team.aliens.dms.android.design_system.color.DormColor
import team.aliens.dms.android.design_system.toast.rememberToast

enum class DormButtonColor(
    val textColor: Color,
    val backgroundColor: Color,
    val disabledColor: Color,
    val rippleColor: Color,
) {
    Blue(
        textColor = DormColor.Gray100,
        backgroundColor = DormColor.DormPrimary,
        disabledColor = DormColor.Lighten100,
        rippleColor = DormColor.Darken200,
    ),

    Gray(
        textColor = DormColor.Gray100,
        backgroundColor = DormColor.Gray600,
        disabledColor = DormColor.Gray500,
        rippleColor = DormColor.Gray800,
    ),

    Red(
        textColor = DormColor.Gray100,
        backgroundColor = DormColor.Error,
        disabledColor = Color(0xFFFF7373),
        rippleColor = Color(0xFFBB0000),
    ),
}


@Stable
private val DefaultButtonRound = 5.dp

@DormDeprecated
@Composable
fun DormContainedLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    round: Dp = DefaultButtonRound,
    color: DormButtonColor,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedRoundLargeButton(
        modifier = modifier,
        text = text,
        textColor = color.textColor,
        round = round,
        backgroundColor = color.backgroundColor,
        rippleColor = color.rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@DormDeprecated
@Composable
fun DormOutlineLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    round: Dp = DefaultButtonRound,
    color: DormButtonColor,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicOutlinedRoundLargeButton(
        modifier = modifier,
        text = text,
        textColor = color.backgroundColor,
        round = round,
        backgroundColor = color.backgroundColor,
        disabledColor = color.disabledColor,
        rippleColor = color.rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@DormDeprecated
@Composable
fun DormContainedDefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    round: Dp = DefaultButtonRound,
    color: DormButtonColor,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedRoundButton(
        modifier = modifier,
        text = text,
        textColor = color.textColor,
        round = round,
        backgroundColor = color.backgroundColor,
        rippleColor = color.rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@DormDeprecated
@Composable
fun DormOutlinedDefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    round: Dp = DefaultButtonRound,
    color: DormButtonColor,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BasicOutlinedRoundButton(
        modifier = modifier,
        text = text,
        round = round,
        textColor = color.backgroundColor,
        backgroundColor = color.backgroundColor,
        disabledColor = color.disabledColor,
        rippleColor = color.rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@Preview
@Composable
fun ButtonPreview() {
    val scrollState = rememberScrollState()
    val toast = rememberToast()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DormContainedLargeButton(
            text = "로그인",
            color = DormButtonColor.Blue,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormContainedLargeButton(
            text = "로그인",
            color = DormButtonColor.Gray,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormContainedLargeButton(
            text = "로그인",
            color = DormButtonColor.Red,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormContainedLargeButton(
            text = "로그인",
            color = DormButtonColor.Blue,
            enabled = false,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormContainedLargeButton(
            text = "로그인",
            color = DormButtonColor.Gray,
            enabled = false,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormContainedLargeButton(
            text = "로그인",
            color = DormButtonColor.Red,
            enabled = false,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormOutlineLargeButton(
            text = "로그인",
            color = DormButtonColor.Blue,
        ) {
            toast(
                message = "btn clicked"
            )

        }

        DormOutlineLargeButton(
            text = "로그인",
            color = DormButtonColor.Gray,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormOutlineLargeButton(
            text = "로그인",
            color = DormButtonColor.Red,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormOutlineLargeButton(
            text = "로그인",
            color = DormButtonColor.Red,
            enabled = false,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormOutlineLargeButton(
            text = "로그인",
            color = DormButtonColor.Gray,
            enabled = false,
        ) {
            toast(
                message = "btn clicked"
            )
        }

        DormOutlineLargeButton(
            text = "로그인",
            color = DormButtonColor.Blue,
            enabled = false,
        ) {
            toast(
                message = "btn clicked"
            )
        }
    }
}