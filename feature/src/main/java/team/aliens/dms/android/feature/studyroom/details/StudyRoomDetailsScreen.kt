package team.aliens.dms.android.feature.studyroom.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun StudyRoomDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    studyRoomId: UUID,
    // TODO: change to custom navigation argument type
    studyRoomName: String,
    timeslot: UUID,
) {
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
                }
            )
        }
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {

        }
    }
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

        */
/*val availableTime = LaunchedEffect(Unit) {
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
        */
/*studyRoomDetailsViewModel.onEvent(
            event = StudyRoomDetailsViewModel.UiEvent.CancelApplySeat(
                seatId = currentSeat.value!!,
                timeSlot = timeSlot,
            )
        )*//*

    }

    val onApply: () -> Unit = {
       */
/* if (currentSeat.value == null) {
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
