package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.Body5
import com.example.design_system.typography.OverLine
import java.util.UUID

private val SeatSize: DpSize = DpSize(
    width = 40.dp,
    height = 40.dp,
)

@Composable
fun SeatContent(
    seatId: String,
    color: Color,
    text: String,
    textColor: Color = DormColor.Gray100,
    enabled: Boolean = true,
    onClick: (String) -> Unit,
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
                onClick(seatId)
            },
        contentAlignment = Alignment.Center,
    ) {
        OverLine(
            text = text,
            color = textColor,
        )
    }
}

@Composable
fun RoomContent(
    roomId: String,
    position: String,
    title: String,
    currentNumber: Int,
    maxNumber: Int,
    condition: String,
    onClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(91.dp)
            .background(DormColor.Gray100)
            .dormClickable {
                onClick(roomId)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                )
        ) {
            Row(
                modifier =
                    Modifier.fillMaxWidth()
            ) {
                Body5(
                    text = position,
                    color = DormColor.DormPrimary,
                )
                
                Spacer(modifier = Modifier.width(14.dp))

                Body5(text = title)

                Spacer(modifier = Modifier.weight(1f))

                Body5(
                    text = "$currentNumber / $maxNumber",
                    color = DormColor.Gray500,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Body5(
                text = condition,
                color = DormColor.DormPrimary,
            )
        }
    }
}

@Preview
@Composable
fun PreviewSeatContent() {
    Column {
        SeatContent(
            seatId = "b77eafed-69ab-422d-8448-1ec1f0a2eb8c",
            color = DormColor.DormPrimary,
            text = "임세현",
            enabled = true,
        ) { seatId ->
            print(seatId)
        }

        RoomContent(
            roomId = "b77eafed-69ab-422d-8448-1ec1f0a2eb8c",
            position = "2층",
            title = "제목입니다.",
            currentNumber = 5,
            maxNumber = 8,
            condition = "2학년 남자",
            onClick = { roomId ->
                print(roomId)
            }
        )
    }
}