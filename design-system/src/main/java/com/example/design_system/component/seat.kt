package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
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

enum class SeatType {
    // ?????????
    IN_USE,

    // ????????????
    AVAILABLE,

    // ????????????
    UNAVAILABLE,

    // ???????????? ??????
    EMPTY;

    companion object {
        fun toSeatType(type: String) = values().firstOrNull {
            it.toString() == type
        } ?: EMPTY
    }
}

/**
 * ????????? ????????? ???????????? data class
 *
 * ????????? ????????? 3?????? ????????? ????????????.
 *
 * @param id ?????? UUID
 * @param number ?????? ??????
 * @param name ????????? ???????????? ????????? ??????
 * @param color ????????? ??????
 */
data class SeatItem(
    val id: String? = null,
    val number: Int? = null,
    val name: String? = null,
    val color: Color = DormColor.DormPrimary,
    val type: SeatType = SeatType.EMPTY,
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
    selected: String,
    onSelectedChanged: (String) -> Unit,
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
                selected = selected,
                onSelectedChanged = onSelectedChanged,
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
    isSelected: Boolean = false,
    onSelectedChanged: (String) -> Unit,
    clickedEnabled: Boolean = true
) {
    if (isSelected) {
        Box(
            modifier = Modifier
                .size(SeatSize)
                .border(
                    width = 1.dp,
                    color = color,
                    shape = CircleShape,
                )
                .background(
                    color = Color.White,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            OverLine(
                text = text,
                color = Color.Black,
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(SeatSize)
                .background(
                    color = color,
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .dormClickable(
                    runIf = clickedEnabled,
                ) {
                    onSelectedChanged(seatId)
                },
            contentAlignment = Alignment.Center,
        ) {
            OverLine(
                text = text,
                color = textColor,
            )
        }
    }
}

@Composable
private fun SeatListContent(
    modifier: Modifier = Modifier,
    seats: List<List<SeatItem>>,
    selected: String,
    onSelectedChanged: (String) -> Unit,
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
                    when (seat.type) {
                        SeatType.EMPTY -> {
                            Spacer(modifier = Modifier.size(SeatSize))
                        }
                        SeatType.IN_USE -> {
                            SeatContent(
                                seatId = seat.id ?: "",
                                color = seat.color,
                                text = seat.name ?: seat.number.toString(),
                                isSelected = selected == seat.id,
                                onSelectedChanged = { seatId ->
                                    onSelectedChanged(seatId)
                                },
                                clickedEnabled = false,
                            )
                        }
                        SeatType.AVAILABLE -> {
                            SeatContent(
                                seatId = seat.id ?: "",
                                color = seat.color,
                                isSelected = selected == seat.id,
                                text = seat.number.toString(),
                                onSelectedChanged = { seatId ->
                                    onSelectedChanged(seatId)
                                },
                            )
                        }
                        SeatType.UNAVAILABLE -> {
                            Box(
                                modifier = Modifier
                                    .size(SeatSize)
                                    .clip(CircleShape)
                                    .background(color = DormColor.Gray400),
                                contentAlignment = Alignment.Center,
                            ) {
                                OverLine(
                                    text = "??????",
                                    color = Color.White,
                                )
                            }
                        }
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
    isMine: Boolean,
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
            Row(
                modifier =
                Modifier.fillMaxWidth()
            ) {
                Body5(
                    text = condition,
                    color = DormColor.DormPrimary,
                )
                if (isMine) {
                    DormContainedLargeButton(
                        modifier = Modifier
                            .height(24.dp)
                            .width(52.dp),
                        text = "?????????",
                        color = DormButtonColor.Blue
                    ) {

                    }
                }
            }
        }
    }
}

data class SeatTypeUiModel(
    val color: Color,
    val text: String,
)

@Composable
fun SeatTypeList(
    items: List<SeatTypeUiModel>,
) {
    LazyRow(
        contentPadding = PaddingValues(
            start = 2.dp,
        ),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        items(items) { seatType ->
            SeatTypeContent(item = seatType)
        }
    }
}

@Composable
private fun SeatTypeContent(
    item: SeatTypeUiModel,
) {
    Row(
        modifier = Modifier
            .height(20.dp)
            .padding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = item.color,
                    shape = CircleShape,
                )
                .clip(CircleShape),
        )
        OverLine(text = item.text)
    }
}

@Preview
@Composable
fun PreviewSeatContent() {
    Column {
        SeatTypeList(
            items = listOf(
                SeatTypeUiModel(DormColor.DormPrimary, "?????????"),
                SeatTypeUiModel(DormColor.Gray800, "??????"),
                SeatTypeUiModel(DormColor.DormPrimary, "?????????")
            )
        )
    }
}
