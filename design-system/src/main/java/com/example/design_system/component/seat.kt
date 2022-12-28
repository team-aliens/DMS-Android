package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.constans.asLoose
import com.example.design_system.modifier.dormClickable
import com.example.design_system.modifier.innerShadow
import com.example.design_system.typography.Body5
import com.example.design_system.typography.DormTypography
import com.example.design_system.typography.OverLine
import kotlin.random.Random

private val SeatSize: DpSize = DpSize(
    width = 40.dp,
    height = 40.dp,
)

private const val RoomSeatContentLayoutId = "RoomSeatsContent"
private const val RoomTopDescriptionLayoutId = "RoomTopDescription"
private const val RoomBottomDescriptionLayoutId = "RoomBottomDescription"
private const val RoomStartDescriptionLayoutId = "RoomStartDescription"
private const val RoomEndDescriptionLayoutId = "RoomEndDescription"

private const val RoomDescriptionPaddingInt: Int = 12

private val RoomBoxShape = RoundedCornerShape(22.dp)

/**
 * 자습실 좌석을 구성하는 data class
 *
 * 자습실 좌석은 3가지 경우로 나뉩니다.
 *
 * 1. 빈 좌석인 경우 (id == null && text == null)
 * 2. 사용 중인 좌석인 경우 (id != null && name != null)
 * 3. 사용 가능한 좌석인 경우 (id != null && name == null)
 *
 * @param id 좌석 UUID
 * @param number 좌석 넘버
 * @param name 좌석을 사용중인 사람의 이름
 * @param color 좌석의 색갈
 */
data class SeatItem(
    val id: String? = null,
    val number: Int? = null,
    val name: String? = null,
    val color: Color = DormColor.DormPrimary,
    val isApplication: Boolean = false,
)

@Composable
private fun RoomDescription(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.roomDescription,
        color = DormColor.Lighten100,
    )
}

@Composable
fun RoomDetail(
    topDescription: String,
    bottomDescription: String,
    startDescription: String,
    endDescription: String,
    seats: List<List<SeatItem>>,
    onClick: (String) -> Unit,
) {
    Layout(
        modifier = Modifier
            .fillMaxWidth()
            .height(328.dp)
            .background(
                color = Color.White,
                shape = RoomBoxShape,
            )
            .border(
                width = 1.dp,
                color = DormColor.DormPrimary,
                shape = RoomBoxShape,
            ),
        content = {
            SeatListContent(
                modifier = Modifier
                    .layoutId(RoomSeatContentLayoutId)
                    .padding(30.dp)
                    .innerShadow(
                        color = Color.White,
                        blur = 20.dp
                    ),
                seats = seats,
                onClick = onClick,
            )
            RoomDescription(
                modifier = Modifier.layoutId(RoomTopDescriptionLayoutId),
                text = topDescription,
            )
            RoomDescription(
                modifier = Modifier.layoutId(RoomBottomDescriptionLayoutId),
                text = bottomDescription,
            )
            RoomDescription(
                modifier = Modifier.layoutId(RoomStartDescriptionLayoutId),
                text = startDescription,
            )
            RoomDescription(
                modifier = Modifier.layoutId(RoomEndDescriptionLayoutId),
                text = endDescription,
            )

        }
    ) { measurables, constraints ->
        val looseConstraints = constraints.asLoose()
        val extraLooseConstraints = constraints.asLoose(width = true)

        val seatsPlaceable = measurables.firstOrNull { measurable ->
            measurable.layoutId == RoomSeatContentLayoutId
        }?.measure(looseConstraints) ?: throw NullPointerException()

        val topDescriptionPlaceable = measurables.firstOrNull { measurable ->
            measurable.layoutId == RoomTopDescriptionLayoutId
        }?.measure(extraLooseConstraints) ?: throw NullPointerException()

        val bottomDescriptionPlaceable = measurables.firstOrNull { measurable ->
            measurable.layoutId == RoomBottomDescriptionLayoutId
        }?.measure(extraLooseConstraints) ?: throw NullPointerException()

        val startDescriptionPlaceable = measurables.firstOrNull { measurable ->
            measurable.layoutId == RoomStartDescriptionLayoutId
        }?.measure(extraLooseConstraints) ?: throw NullPointerException()

        val endDescriptionPlaceable = measurables.firstOrNull { measurable ->
            measurable.layoutId == RoomEndDescriptionLayoutId
        }?.measure(extraLooseConstraints) ?: throw NullPointerException()

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            seatsPlaceable.place(
                x = 0,
                y = 0,
            )

            topDescriptionPlaceable.place(
                x = Alignment.CenterHorizontally.align(
                    size = topDescriptionPlaceable.width,
                    space = constraints.maxWidth,
                    layoutDirection = layoutDirection,
                ),
                y = RoomDescriptionPaddingInt,
            )

            bottomDescriptionPlaceable.place(
                x = Alignment.CenterHorizontally.align(
                    size = bottomDescriptionPlaceable.width,
                    space = constraints.maxWidth,
                    layoutDirection = layoutDirection,
                ),
                y = constraints.maxHeight - bottomDescriptionPlaceable.height - RoomDescriptionPaddingInt,
            )

            startDescriptionPlaceable.place(
                x = RoomDescriptionPaddingInt,
                y = Alignment.CenterVertically.align(
                    size = startDescriptionPlaceable.width,
                    space = constraints.maxHeight,
                )
            )

            endDescriptionPlaceable.place(
                x = constraints.maxWidth - endDescriptionPlaceable.width - RoomDescriptionPaddingInt,
                y = Alignment.CenterVertically.align(
                    size = endDescriptionPlaceable.width,
                    space = constraints.maxHeight,
                )
            )
        }
    }
}

@Composable
private fun SeatContent(
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
private fun SeatListContent(
    modifier: Modifier = Modifier,
    seats: List<List<SeatItem>>,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        seats.map {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                it.map { seat ->
                    if (seat.number != null) {
                        SeatContent(
                            seatId = seat.id ?: "",
                            color = seat.color,
                            text = if (seat.isApplication) seat.number.toString() else seat.name
                                ?: seat.number.toString(),
                            onClick = { seatId ->
                                onClick(seatId)
                            },
                        )
                    } else {
                        Spacer(modifier = Modifier.size(SeatSize))
                    }
                }
            }
        }
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
            .background(
                color = DormColor.Gray100,
                shape = RoundedCornerShape(15),
            )
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

/**
 * 20x20 더미 데이터
 */
private val DummySeat = (0..20).map { a ->
    (0..20).map { b ->
        val random = Random.nextInt(3)
        when (random) {
            0 -> SeatItem(number = 1, name = "유저${a + b}", color = DormColor.DormPrimary, id = "")
            1 -> SeatItem(number = a + b, name = null, color = DormColor.Lighten100, id = "")
            else -> SeatItem(number = null, name = null, id = "")
        }
    }.toList()
}.toList()

@Preview
@Composable
fun PreviewSeatContent() {
    Box(modifier = Modifier.padding(16.dp)) {
        RoomDetail(
            topDescription = "칠판",
            bottomDescription = "칠판",
            startDescription = "칠판",
            endDescription = "칠판",
            seats = DummySeat,
            onClick = { seatId ->
                print(seatId)
            },
        )
    }
}