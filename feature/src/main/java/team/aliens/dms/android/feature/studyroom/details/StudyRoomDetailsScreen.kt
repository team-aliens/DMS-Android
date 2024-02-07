package team.aliens.dms.android.feature.studyroom.details

import androidx.annotation.ColorInt
import androidx.annotation.Size
import androidx.compose.foundation.BorderStroke
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
import team.aliens.dms.android.core.designsystem.DmsScaffold
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
            StudyRoomDetailsIntent.FetchStudyRoomDetails(
                studyRoomId = studyRoomId,
                timeslot = timeslot,
            ),
        )
    }

    DmsScaffold(
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
                        uiState.seatTypes?.let { seatTypes ->
                            SeatTypeList(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalPadding()
                                    .topPadding(),
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
                        )
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "신청하기")
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
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
    ) {
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
) {
    OutlinedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
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
                            Seat(
                                seat = seat,
                            )
                        } else {
                            Spacer(modifier = Modifier.size(DefaultSeatIconSize))
                        }
                    }
                }
            }
        }
    }
}

// TODO: move to StudyRoomDefaults
@Stable
val DefaultSeatIconSize = DpSize(
    width = 48.dp,
    height = 48.dp,
)

@Composable
private fun Seat(
    seat: StudyRoom.Seat,
) {
    ElevatedCard(
        modifier = Modifier.size(DefaultSeatIconSize),
        shape = CircleShape,
        colors = CardDefaults.elevatedCardColors(
            contentColor = DmsTheme.colorScheme.onPrimary, // TODO: determine by container color
            containerColor = Color(seat.type?.color?.let(::parseColor) ?: 0x000000),
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = ShadowDefaults.SmallElevation,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = seat.student?.name ?: "gh",
                style = DmsTheme.typography.overline,
            )
        }
    }
}

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
    val seats = listOf(
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
            number = 1,
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
            number = 1,
            status = StudyRoom.Seat.Status.UNAVAILABLE,
            isMine = false,
            student = StudyRoom.Seat.Student(
                id = UUID.randomUUID(),
                name = "박준수",
            ),
        ),
        StudyRoom.Seat(
            id = UUID.randomUUID(),
            row = 5,
            column = 6,
            type = SEAT_TYPE_POWER,
            number = 1,
            status = StudyRoom.Seat.Status.AVAILABLE,
            isMine = false,
            student = StudyRoom.Seat.Student(
                id = UUID.randomUUID(),
                name = "준박수",
            ),
        ),
    )
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
        countOfColumns = 10
    )
}

/*

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.design_system.button.DormButtonColor
import team.aliens.dms.android.design_system.button.DormContainedLargeButton
import team.aliens.dms.android.design_system.color.DormColor
import team.aliens.dms.android.design_system.component.RoomDetail
import team.aliens.dms.android.design_system.component.RoomItem
import team.aliens.dms.android.design_system.component.SeatItem
import team.aliens.dms.android.design_system.component.SeatType
import team.aliens.dms.android.design_system.component.SeatTypeList
import team.aliens.dms.android.design_system.component.SeatTypeUiModel
import team.aliens.dms.android.design_system.extension.Space
import team.aliens.dms.android.design_system.theme.DormTheme
import team.aliens.dms.android.design_system.toast.rememberToast
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.FloatingNotice
import team.aliens.dms.android.feature._legacy.util.TopBar
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator
import java.util.UUID

data class StudyRoomDetailsNavArgs(
    val studyRoomId: UUID,
    val timeslot: UUID,
)

@SuppressLint("ResourceType")
@Destination(navArgsDelegate = StudyRoomDetailsNavArgs::class)
@Composable
fun StudyRoomDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    studyRoomDetailsViewModel: StudyRoomDetailsViewModel = hiltViewModel(),
) {

        *//*val availableTime = LaunchedEffect(Unit) {
        studyRoomDetailsViewModel.initStudyRoom(
            roomId = roomId, // todo refactor
            timeslot = timeslot,
        )
    }*//*


    val context = LocalContext.current
    val toast = rememberToast()

    val uiState = studyRoomDetailsViewModel.uiState.collectAsState().value
    val currentSeat = uiState.currentSeat.collectAsState(null)

    val onCancel: () -> Unit = {
        *//*studyRoomDetailsViewModel.onEvent(
            event = StudyRoomDetailsViewModel.UiEvent.CancelApplySeat(
                seatId = currentSeat.value!!,
                timeSlot = timeSlot,
            )
        )*//*

    }

    val onApply: () -> Unit = {
       *//* if (currentSeat.value == null) {
            toast(
                context.getString(R.string.study_room_please_first_select),
            )
        } else {
            studyRoomDetailsViewModel.onEvent(
                event = StudyRoomDetailsViewModel.UiEvent.ApplySeat(
                    seat = currentSeat.value.toString(), // todo
                    timeSlot = timeSlot,
                ),
            )
        }*//*

    }

    LaunchedEffect(Unit) {
        studyRoomDetailsViewModel.errorState.collect {
            toast(it)
        }
    }

    LaunchedEffect(Unit) {
        studyRoomDetailsViewModel.uiState.value.errorMessage.collect {
            toast(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DormTheme.colors.background,
            ),
    ) {

        TopBar(
            title = "${uiState.studyRoomDetails.startTime} ~ ${uiState.studyRoomDetails.endTime}",
            onPrevious = navigator::popBackStack,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                ),
        ) {

            Space(
                space = 17.dp,
            )

            FloatingNotice(
                text = stringResource(
                    id = R.string.study_room_apply_time, "${uiState.startAt} ~ ${uiState.endAt}"
                ),
            )

            Space(
                space = 24.dp,
            )

            StudyRoomInformationBox(
                availableGrade = uiState.studyRoomDetails.availableGrade,
                floor = uiState.studyRoomDetails.floor,
                studyRoomName = uiState.studyRoomDetails.name,
                inUseHeadCount = uiState.studyRoomDetails.inUseHeadcount,
                totalAvailableSeat = uiState.studyRoomDetails.totalAvailableSeat,
                koreanValue = uiState.studyRoomDetails.availableSex.koreanValue,
            )

            Space(
                space = 15.dp,
            )

            if (uiState.seatBoolean) { // fixme 의미 파악
                SeatTypeList(
                    items = uiState.seatType.types.map {
                        it.toModel()
                    },
                )
            }

            Space(
                space = 15.dp,
            )

            RoomDetail(
                topDescription = uiState.studyRoomDetails.northDescription,
                bottomDescription = uiState.studyRoomDetails.southDescription,
                startDescription = uiState.studyRoomDetails.westDescription,
                endDescription = uiState.studyRoomDetails.eastDescription,
                seats = uiState.studyRoomDetails.toDesignSystemModel(),
                selected = currentSeat.value.toString(), // todo
            ) { seat ->
                studyRoomDetailsViewModel.onEvent(
                    event = StudyRoomDetailsViewModel.UiEvent.ChangeSelectedSeat(
                        seat = seat,
                    ),
                )
            }

            Space(
                ratio = 1f,
            )

            ActionButtons(
                onCancel = onCancel,
                onApply = onApply,
            )

            Space(
                space = 54.dp,
            )
        }
    }
}

@Composable
private fun ActionButtons(
    onCancel: () -> Unit,
    onApply: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        // Cancel button
        DormContainedLargeButton(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = stringResource(
                id = R.string.cancel,
            ),
            color = DormButtonColor.Gray,
        ) {
            onCancel()
        }

        Space(space = 10.dp)

        // Apply button
        DormContainedLargeButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(
                id = R.string.Application,
            ),
            color = DormButtonColor.Blue,
        ) {
            onApply()
        }
    }
}

@Composable
private fun StudyRoomInformationBox(
    availableGrade: Int,
    floor: Int,
    studyRoomName: String,
    inUseHeadCount: Int,
    totalAvailableSeat: Int,
    koreanValue: String,
) {
    val grade = if (availableGrade == 0) "모든" else availableGrade.toString()

    RoomItem(
        roomId = "",
        position = "${floor}층",
        title = studyRoomName,
        currentNumber = inUseHeadCount,
        maxNumber = totalAvailableSeat,
        condition = grade + stringResource(
            id = R.string.Grade,
        ) + " $koreanValue",
        isMine = false,
        onClick = { },
    )
}

fun FetchSeatTypesOutput.SeatTypeInformation.toModel() = SeatTypeUiModel(
    color = this.color,
    text = this.name,
)

*/
/**
 * [StudyRoomDetailEntity] 를 디자인 시스템인 [RoomDetail] 에서 사용하기 위해
 * List<List<SeatItem>> 형식으로 Mapping 에주는 extension
 *
 * @return 디자인 시스템에서 사용할 수 있는 List<List<SeatItem>> 형식
 *//*

private fun FetchStudyRoomDetailsOutput.toDesignSystemModel(): List<List<SeatItem>> {
    val defaultSeats: MutableList<MutableList<SeatItem>> = List(
        size = this.totalWidthSize,
        init = {
            List(
                size = this.totalHeightSize,
                init = {
                    SeatItem()
                },
            ).toMutableList()
        },
    ).toMutableList()

    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }

    this.seats.map { seat ->
        val width = seat.column - 1
        val height = seat.row - 1
        val color = seat.type?.color

        defaultSeats[height][width] = SeatItem(
            id = seat.id.toString(),
            number = seat.number,
            name = seat.student?.name,
            color = if (color != null) {
                getColor(color)
            } else DormColor.DormPrimary,
            type = SeatType.toSeatType(seat.status.toString()),
        )
    }

    return defaultSeats
}
*/
