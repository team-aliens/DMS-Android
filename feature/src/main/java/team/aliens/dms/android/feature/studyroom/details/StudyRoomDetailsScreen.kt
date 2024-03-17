package team.aliens.dms.android.feature.studyroom.details

import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.toStudyRoom
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.studyroom.StudyRoomCard
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun StudyRoomDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    viewModel: StudyRoomDetailsViewModel = hiltViewModel(),
    studyRoomId: UUID,
    // TODO: change to custom navigation argument type
    studyRoomName: String,
    timeslot: UUID,
    studyRoomApplicationStartTime: String,
    studyRoomApplicationEndTime: String,
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.postIntent(
            StudyRoomDetailsIntent.InitStudyRoomDetails(
                studyRoomId = studyRoomId,
                timeslot = timeslot,
            ),
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = studyRoomName) },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            FloatingNotice(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                text = stringResource(
                    id = R.string.format_study_room_application_time,
                    studyRoomApplicationStartTime,
                    studyRoomApplicationEndTime,
                ),
            )
            uiState.studyRoomDetails?.let { studyRoomDetails ->
                StudyRoomCard(
                    studyRoom = studyRoomDetails.toStudyRoom(),
                    onClick = {},
                )
                ElevatedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = DmsTheme.colorScheme.surface,
                        contentColor = DmsTheme.colorScheme.onSurface,
                    ),
                    shape = DmsTheme.shapes.surfaceLarge,
                    elevation = CardDefaults.outlinedCardElevation(
                        defaultElevation = ShadowDefaults.SmallElevation,
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
                    ) {
                        Spacer(modifier = Modifier.topPadding())
                        uiState.seatTypes?.let { seatTypes ->
                            SeatTypeList(
                                modifier = Modifier.fillMaxWidth(),
                                seatTypes = seatTypes,
                            )
                        }
                        SeatLayout(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .horizontalPadding(),
                            seats = studyRoomDetails.seats,
                            countOfColumns = studyRoomDetails.totalWidthSize,
                            countOfRows = studyRoomDetails.totalHeightSize,
                            northDescription = studyRoomDetails.northDescription,
                            eastDescription = studyRoomDetails.eastDescription,
                            westDescription = studyRoomDetails.westDescription,
                            southDescription = studyRoomDetails.southDescription,
                            selectedSeat = uiState.selectedSeat,
                            onSelectSeat = { seat ->
                                viewModel.postIntent(
                                    StudyRoomDetailsIntent.SelectSeat(seat = seat),
                                )
                            },
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalPadding()
                                .bottomPadding(),
                            onClick = {
                                viewModel.postIntent(StudyRoomDetailsIntent.UpdateSeat)
                            },
                            enabled = uiState.mainButtonState != null,
                            colors = when (uiState.mainButtonState) {
                                StudyRoomDetailsMainButtonState.CANCEL_SEAT -> ButtonDefaults.containedRefuseButtonColors()
                                else -> ButtonDefaults.containedButtonColors()
                            }
                        ) {
                            Text(
                                text = stringResource(
                                    id = when (uiState.mainButtonState) {
                                        StudyRoomDetailsMainButtonState.CANCEL_SEAT -> R.string.study_room_button_cancel_seat
                                        null -> R.string.study_room_button_please_select_seat_before_application
                                        else -> R.string.study_room_button_apply_seat
                                    },
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeatTypeList(
    modifier: Modifier = Modifier,
    seatTypes: List<StudyRoom.Seat.Type>,
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
    ) {
        Spacer(modifier = Modifier.width(DefaultHorizontalSpace))
        seatTypes.forEach { seatType ->
            SeatType(
                indicator = {
                    SeatTypeIndicator(color = Color(parseColor(seatType.color)))
                },
                text = {
                    Text(text = seatType.name)
                },
            )
        }
        Spacer(modifier = Modifier.width(DefaultHorizontalSpace))
    }
}

@Composable
private fun SeatTypeIndicator(
    color: Color,
    size: DpSize = DpSize(
        width = 10.dp,
        height = 10.dp,
    ),
) {
    Surface(
        modifier = Modifier.size(size),
        shape = CircleShape,
        color = color,
    ) {}
}

@Composable
private fun SeatType(
    modifier: Modifier = Modifier,
    indicator: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        indicator()
        ProvideTextStyle(
            value = DmsTheme.typography.overline.copy(
                color = DmsTheme.colorScheme.onSurface,
            ),
        ) {
            text()
        }
    }
}

@Composable
private fun SeatLayout(
    modifier: Modifier = Modifier,
    seats: List<StudyRoom.Seat>?,
    countOfColumns: Int,
    countOfRows: Int,
    northDescription: String,
    eastDescription: String,
    westDescription: String,
    southDescription: String,
    selectedSeat: StudyRoom.Seat?,
    onSelectSeat: (seat: StudyRoom.Seat) -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        shape = DmsTheme.shapes.surfaceLarge,
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = ShadowDefaults.SmallElevation,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DmsTheme.colorScheme.primary,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            // TODO: 텍스트 함수 재활용 할 수 있도록 구현
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .topPadding(),
                text = northDescription,
                color = DmsTheme.colorScheme.primaryContainer,
                style = DmsTheme.typography.body3,
            )
            Text(
                modifier = Modifier
                    .rotate(90f)
                    .align(Alignment.CenterEnd)
                    .endPadding(), // FIXME: RTL 글자 형식에서 잘못된 표기 발생
                text = eastDescription,
                color = DmsTheme.colorScheme.primaryContainer,
                style = DmsTheme.typography.body3,
            )
            Text(
                modifier = Modifier
                    .rotate(-90f)
                    .align(Alignment.CenterStart)
                    .startPadding(), // FIXME: RTL 글자 형식에서 잘못된 표기 발생
                text = westDescription,
                color = DmsTheme.colorScheme.primaryContainer,
                style = DmsTheme.typography.body3,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .bottomPadding(),
                text = southDescription,
                color = DmsTheme.colorScheme.primaryContainer,
                style = DmsTheme.typography.body3,
            )
            seats?.let { seats ->
                SeatList(
                    seats = seats,
                    countOfColumns = countOfColumns,
                    countOfRows = countOfRows,
                    selectedSeat = selectedSeat,
                    onSelectSeat = onSelectSeat,
                )
            }
        }
    }
}

@Composable
private fun SeatList(
    modifier: Modifier = Modifier,
    seats: List<StudyRoom.Seat>,
    countOfColumns: Int,
    countOfRows: Int,
    selectedSeat: StudyRoom.Seat?,
    onSelectSeat: (seat: StudyRoom.Seat) -> Unit,
) {
    // FIXME: 리컴포지션 방지하도록 작성하기
    val formedSeats: List<Array<StudyRoom.Seat?>> = List(
        size = countOfRows
    ) { indexOfRows ->
        arrayOfNulls<StudyRoom.Seat?>(size = countOfColumns)
    }.apply {
        seats.forEach { seat ->
            val rowIndexOfSeat = seat.row - 1
            val columnIndexOfSeat = seat.column - 1
            this[rowIndexOfSeat][columnIndexOfSeat] = seat
        }
    }

    Box(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 40.dp,
                vertical = 40.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            formedSeats.forEach { column ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    column.forEach { seat ->
                        if (seat != null) {
                            val selected = seat == selectedSeat
                            Seat(
                                seat = seat,
                                onClick = onSelectSeat,
                                selected = selected,
                            )
                        } else {
                            Spacer(modifier = Modifier.size(DefaultSeatButtonSize))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Seat(
    seat: StudyRoom.Seat,
    onClick: (seat: StudyRoom.Seat) -> Unit,
    selected: Boolean,
) {
    val enabled = seat.student == null || seat.isMine
    val baseColor = try {
        Color(parseColor(seat.type?.color!!))
    } catch (_: Exception) {
        DmsTheme.colorScheme.onPrimary
    }

    Button(
        modifier = Modifier.size(DefaultSeatButtonSize),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            // TODO: determine by container color
            contentColor = if (seat.isMine) {
                baseColor
            } else {
                if (selected) {
                    baseColor
                } else {
                    DmsTheme.colorScheme.onPrimary
                }
            },
            containerColor = if (seat.isMine) {
                DmsTheme.colorScheme.onPrimary
            } else {
                if (selected) {
                    DmsTheme.colorScheme.onPrimary
                } else {
                    baseColor
                }
            },
        ),
        onClick = { onClick(seat) },
        enabled = enabled,
        contentPadding = NoPadding,
        border = if (seat.isMine) {
            if (selected) {
                BorderStroke(
                    width = 1.dp,
                    color = DmsTheme.colorScheme.primary,
                )
            } else {
                BorderStroke(
                    width = 2.dp,
                    color = baseColor,
                )
            }
        } else {
            if (selected) {
                BorderStroke(
                    width = 1.dp,
                    color = DmsTheme.colorScheme.primary,
                )
            } else {
                null
            }
        },
    ) {
        Text(
            text = if (seat.isMine || (seat.student == null)) {
                seat.number.toString()
            } else {
                seat.student!!.name
            }
        )
    }
}

// TODO: move to StudyRoomDefaults
@Stable
val DefaultSeatButtonSize = DpSize(
    width = 52.dp,
    height = 52.dp,
)

// TODO: move to StudyRoomDefaults
@Stable
val NoPadding = PaddingValues(0.dp)

/**
 * copied from https://stackoverflow.com/questions/60247480/color-from-hex-string-in-jetpack-compose
 */
@ColorInt
fun parseColor(@Size(min = 1) colorString: String): Int = if (colorString[0] == '#') {
    var color = colorString.substring(1).toLong(16)
    if (colorString.length == 7) { // Set the alpha value
        color = color or -0x1000000
    } else require(colorString.length == 9) { "Unknown color" }
    color.toInt()
} else {
    var color = colorString.toLong(16)
    if (colorString.length == 7) { // Set the alpha value
        color = color or -0x1000000
    } else require(colorString.length == 9) { "Unknown color" }
    color.toInt()
}

// TODO: move to test directory
@Preview(
    showSystemUi = true,
)
@Composable
private fun SeatLayoutPreview() {
    val SEAT_TYPE_NORMAL = StudyRoom.Seat.Type(
        id = UUID.randomUUID(),
        name = "일반",
        color = "#FF3366FF",
    )
    val SEAT_TYPE_POWER = StudyRoom.Seat.Type(
        id = UUID.randomUUID(),
        name = "전기 콘센트",
        color = "#FF88FF04",
    )
    val seats by remember {
        mutableStateOf(
            listOf(
                StudyRoom.Seat(
                    id = UUID.randomUUID(),
                    row = 1,
                    column = 1,
                    type = SEAT_TYPE_NORMAL,
                    number = 1,
                    status = StudyRoom.Seat.Status.AVAILABLE,
                    isMine = false,
                    student = StudyRoom.Seat.Student(
                        id = UUID.randomUUID(),
                        name = "박준수",
                    ),
                ),
                StudyRoom.Seat(
                    id = UUID.randomUUID(),
                    row = 1,
                    column = 2,
                    type = SEAT_TYPE_POWER,
                    number = 2,
                    status = StudyRoom.Seat.Status.AVAILABLE,
                    isMine = true,
                    student = StudyRoom.Seat.Student(
                        id = UUID.randomUUID(),
                        name = "준박수",
                    ),
                ),
                StudyRoom.Seat(
                    id = UUID.randomUUID(),
                    row = 4,
                    column = 5,
                    type = SEAT_TYPE_NORMAL,
                    number = 62,
                    status = StudyRoom.Seat.Status.UNAVAILABLE,
                    isMine = false,
                    student = null,
                ),
                StudyRoom.Seat(
                    id = UUID.randomUUID(),
                    row = 5,
                    column = 6,
                    type = SEAT_TYPE_POWER,
                    number = 412,
                    status = StudyRoom.Seat.Status.AVAILABLE,
                    isMine = false,
                    student = null,
                ),
            ),
        )
    }

    val (selectedSeat, onSelectSeat) = remember {
        mutableStateOf<StudyRoom.Seat?>(null)
    }
    Column {
        SeatLayout(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding()
                .height(450.dp),
            seats = seats,
            northDescription = "북한",
            eastDescription = "러시아",
            westDescription = "중국",
            southDescription = "일본",
            countOfRows = 10,
            countOfColumns = 10,
            selectedSeat = selectedSeat,
            onSelectSeat = onSelectSeat,
        )
        Text(text = "selected: ${selectedSeat?.number}")
    }
}