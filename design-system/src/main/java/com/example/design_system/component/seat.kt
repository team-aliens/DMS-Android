package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.OverLine

private val SeatSize: DpSize = DpSize(
    width = 40.dp,
    height = 40.dp,
)

@Composable
fun SeatContent(
    color: Color,
    text: String,
    textColor: Color = DormColor.Gray100,
    enabled: Boolean = true,
    onClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(SeatSize)
            .background(
                color = color,
                shape = CircleShape,
            )
            .clip(CircleShape)
            .dormClickable(
                runIf = enabled,
            ) {
                onClicked()
            },
        contentAlignment = Alignment.Center,
    ) {
        OverLine(
            text = text,
            color = textColor,
        )
    }

}

@Preview
@Composable
fun PreviewSeatContent() {
    SeatContent(
        color = DormColor.DormPrimary,
        text = "임세현",
        enabled = true,
    ) {

    }
}