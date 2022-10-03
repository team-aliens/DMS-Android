package com.example.design_system.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor

@Stable
private val DefaultButtonRound = 5.dp

@Composable
fun BlueLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    round: Dp = DefaultButtonRound,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedRoundLargeButton(
        modifier = modifier,
        text = text,
        textColor = DormColor.Gray100,
        round = round,
        backgroundColor = DormColor.DormPrimary,
        disbledColor = DormColor.Lighten100,
        rippleColor = DormColor.Darken100,
        rippleEnabled = rippleEnabled,
        onClick = onClick
    )
}

@Preview
@Composable
fun ButtonPreview() {
    var state by remember { mutableStateOf(true) }

    BlueLargeButton(text = "로그인") {
        state = !state
    }
}