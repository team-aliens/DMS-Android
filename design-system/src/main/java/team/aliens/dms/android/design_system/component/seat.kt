package team.aliens.dms.android.design_system.component

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import team.aliens.dms.android.design_system.annotation.DormDeprecated
import team.aliens.dms.android.design_system.color.DormColor
import team.aliens.dms.android.design_system.constans.asLoose
import team.aliens.dms.android.design_system.extension.Space
import team.aliens.dms.android.design_system.modifier.dormClickable
import team.aliens.dms.android.design_system.modifier.dormShadow
import team.aliens.dms.android.design_system.modifier.innerShadow
import team.aliens.dms.android.design_system.theme.DormTheme
import team.aliens.dms.android.design_system.typography.Body5
import team.aliens.dms.android.design_system.typography.DormTypography
import team.aliens.dms.android.design_system.typography.OverLine

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
    // 이용중
    IN_USE,

    // 사용가능
    AVAILABLE,

    // 사용불가
    UNAVAILABLE,

    // 비어있는 자리
    EMPTY;

    companion object {
        fun toSeatType(type: String) = values().firstOrNull {
            it.toString() == type
        } ?: EMPTY
    }
}

/**
 * 자습실 좌석을 구성하는 data class
 *
 * 자습실 좌석은 3가지 경우로 나뉩니다.
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
    val type: SeatType = SeatType.EMPTY,
)

@DormDeprecated
@Composable
private fun RoomDescription(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = DormTypography.roomDescription,
        color = DormTheme.colors.secondary,
    )
}

@DormDeprecated
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
    Layout(modifier = Modifier
        .fillMaxWidth()
        .height(328.dp)
        .background(
            color = Color.White,
            shape = RoomBoxShape,
        )
        .border(
            width = 1.dp,
            color = DormTheme.colors.primary,
            shape = RoomBoxShape,
        ), content = {
        SeatListContent(
            modifier = Modifier
                .layoutId(RoomSeatContentLayoutId)
                .padding(30.dp)
                .innerShadow(color = Color.White, blur = 20.dp),
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

    }) { measurables, constraints ->
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

        layout(width = constraints.maxWidth, height = constraints.maxHeight) {
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

@DormDeprecated
@Composable
private fun SeatContent(
    seatId: String,
    color: Color,
    text: String,
    textColor: Color = DormColor.Gray100,
    isSelected: Boolean = false,
    onSelectedChanged: (String) -> Unit,
    clickedEnabled: Boolean = true,
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

@DormDeprecated
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
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
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
                                text = seat.name ?: seat.number.toString(),
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
                                    .background(color = DormTheme.colors.secondaryVariant),
                                contentAlignment = Alignment.Center,
                            ) {
                                OverLine(
                                    text = "불가",
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

@DormDeprecated
@Composable
fun RoomItem(
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
            .dormShadow(color = DormTheme.colors.primaryVariant)
            .clip(RoundedCornerShape(10.dp))
            .background(color = DormTheme.colors.surface),
    ) {
        Column(
            modifier = Modifier
                .dormClickable {
                    onClick(roomId)
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                ),
        ) {

            // room information
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {

                // floor
                Body5(
                    text = position,
                    color = DormTheme.colors.primary,
                )


                Space(14.dp)


                // title
                Body5(
                    text = title,
                )

                // TODO refactoring spacer
                Space(ratio = 1f)


                // reserved seat
                Body5(
                    text = "$currentNumber / $maxNumber",
                    color = DormTheme.colors.primaryVariant,
                )
            }


            Space(space = 14.dp)

            Row {
                // available gender
                Body5(
                    text = condition,
                    color = DormTheme.colors.primary,
                )

                Space(
                    ratio = 1f,
                )

                if (isMine) {
                    LastAppliedItem(
                        text = "신청함",
                        modifier = StudyRoomAppliedTagSize,
                        backgroundColor = DormTheme.colors.primary,
                        textColor = DormTheme.colors.onError,
                    )
                }
            }
        }
    }
}


data class SeatTypeUiModel(
    var color: String,
    val text: String,
)

@DormDeprecated
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

@SuppressLint("ResourceType")
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
                    color = Color(item.color.toColorInt()),
                    shape = CircleShape,
                )
                .clip(CircleShape),
        )
        OverLine(text = item.text)
    }
}