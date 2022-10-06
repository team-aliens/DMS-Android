package com.example.design_system.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor

enum class DormButtonColor(
    val textColor: Color,
    val backgroundColor: Color,
    val disbledColor: Color,
    val rippleColor: Color,
) {
    Blue(
        textColor = DormColor.Gray100,
        backgroundColor = DormColor.DormPrimary,
        disbledColor = DormColor.Lighten100,
        rippleColor = DormColor.Darken100,
    ),

    Gray(
        textColor = DormColor.Gray100,
        backgroundColor = DormColor.Gray600,
        disbledColor = DormColor.Gray300,
        rippleColor = DormColor.Gray800,
    ),

    Red(
        textColor = DormColor.Gray100,
        backgroundColor = DormColor.Error,
        disbledColor = Color(0xFFFF7373),
        rippleColor = Color(0xFFFF4646),
    ),
}


@Stable
private val DefaultButtonRound = 5.dp

@Composable
fun DormLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    round: Dp = DefaultButtonRound,
    color: DormButtonColor,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedRoundLargeButton(
        modifier = modifier,
        text = text,
        textColor = color.textColor,
        round = round,
        backgroundColor = color.backgroundColor,
        disbledColor = color.disbledColor,
        rippleColor = color.rippleColor,
        rippleEnabled = rippleEnabled,
        onClick = onClick
    )
}

@Preview
@Composable
fun ButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DormLargeButton(
            text = "로그인",
            color = DormButtonColor.Blue,
        ) {
        }

        DormLargeButton(
            text = "로그인",
            color = DormButtonColor.Gray,
        ) {
        }

        DormLargeButton(
            text = "로그인",
            color = DormButtonColor.Red,
        ) {
        }

        DormLargeButton(
            text = "로그인",
            color = DormButtonColor.Blue,
            rippleEnabled = false,
        ) {
        }

        DormLargeButton(
            text = "로그인",
            color = DormButtonColor.Gray,
            rippleEnabled = false,
        ) {
        }

        DormLargeButton(
            text = "로그인",
            color = DormButtonColor.Red,
            rippleEnabled = false,
        ) {
        }
    }
}