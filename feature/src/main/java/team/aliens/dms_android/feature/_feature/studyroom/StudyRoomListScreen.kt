package team.aliens.dms_android.feature._feature.studyroom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.RoomItem
import team.aliens.design_system.dialog.DormBottomAlignedContainedLargeButtonDialog
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.feature.component.FloatingNotice
import team.aliens.dms_android.feature.R
import team.aliens.dms_android.feature.util.TopBar
import team.aliens.dms_android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import java.util.UUID

@Destination
@Composable
fun StudyRoomsScreen(
    onPrevious: () -> Unit,
    onNavigateToStudyRoomDetails: (
        studyRoomId: UUID,
        timeslot: UUID,
    ) -> Unit,
    studyRoomListViewModel: StudyRoomListViewModel = hiltViewModel(),
) {

    val toast = rememberToast()

    val studyRoomState = studyRoomListViewModel.uiState.collectAsState().value

    val studyRoomAvailableTimeList = studyRoomState.studyRoomAvailableTime

    var selectedAvailableTimeItemIndex by remember { mutableStateOf(0) }

    var selectedAvailableTime by remember { mutableStateOf("") }

    var showTimeFilterDialogState by remember { mutableStateOf(false) }

    LaunchedEffect(studyRoomAvailableTimeList) {
        if (studyRoomAvailableTimeList.isNotEmpty()) {
            selectedAvailableTime = makeTimeRange(studyRoomAvailableTimeList.first())
        }
    }

    val onStudyRoomFilterDialogDismiss = {
        showTimeFilterDialogState = false
    }

    LaunchedEffect(Unit) {
        with(studyRoomListViewModel) {
            onEvent(event = StudyRoomListViewModel.UiEvent.FetchStudyRoomAvailableTimes)
            errorState.collect {
                toast(it)
            }
        }
    }

    LaunchedEffect(studyRoomAvailableTimeList) {
        if (studyRoomAvailableTimeList.isNotEmpty()) {

            val studyRoomFirstEntity = studyRoomAvailableTimeList.first()

            selectedAvailableTime = makeTimeRange(studyRoomFirstEntity)

            studyRoomListViewModel.onEvent(
                event = StudyRoomListViewModel.UiEvent.FetchStudyRooms(
                    timeSlot = studyRoomAvailableTimeList.first().id,
                )
            )
        }
    }

    if (showTimeFilterDialogState) {
        DormCustomDialog(
            onDismissRequest = {
                showTimeFilterDialogState = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
        ) {
            DormBottomAlignedContainedLargeButtonDialog(
                btnText = stringResource(
                    id = R.string.accept,
                ),
                btnColor = DormButtonColor.Blue,
                onBtnClick = {
                    showTimeFilterDialogState = false
                    selectedAvailableTime =
                        makeTimeRange(studyRoomAvailableTimeList[selectedAvailableTimeItemIndex])
                    studyRoomListViewModel.onEvent(
                        event = StudyRoomListViewModel.UiEvent.FetchStudyRooms(
                            timeSlot = studyRoomAvailableTimeList[selectedAvailableTimeItemIndex].id,
                        ),
                    )
                },
                onDismissRequest = onStudyRoomFilterDialogDismiss,
            ) {

                Title3(
                    text = stringResource(R.string.study_room_time),
                )

                // TODO refactor this spacer
                Spacer(
                    modifier = Modifier.height(24.dp),
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    content = {
                        itemsIndexed(
                            items = studyRoomAvailableTimeList,
                        ) { index, items ->
                            DormTimeChip(
                                selected = (selectedAvailableTimeItemIndex == index),
                                text = makeTimeRange(
                                    studyRoomAvailableTimeList = items,
                                ),
                                onClick = {
                                    selectedAvailableTimeItemIndex = index
                                },
                            )
                        }
                    },
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TopBar(
            title = stringResource(
                id = R.string.ApplicateStudyRoom,
            ),
            onPrevious = onPrevious,
        )


        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedVisibility(
                visible = studyRoomState.hasApplyTime,
            ) {
                StudyRoomsApplicationTimeCard(
                    text = stringResource(
                        id = R.string.study_room_apply_time,
                        "${studyRoomState.startAt} ~ ${studyRoomState.endAt}",
                    ),
                )
            }
            if (studyRoomAvailableTimeList.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp)
                            .dormClickable {
                                showTimeFilterDialogState = true
                            },
                        painter = painterResource(
                            id = R.drawable.ic_slider,
                        ),
                        contentDescription = null,
                    )
                    Body3(
                        text = selectedAvailableTime,
                        color = DormTheme.colors.primary,
                    )
                }
            }
            ListStudyRooms(
                studyRooms = studyRoomState.studyRooms,
                onClick = { seatId -> // todo refactor
                    onNavigateToStudyRoomDetails(
                        seatId,
                        studyRoomAvailableTimeList[selectedAvailableTimeItemIndex].id,
                    )
                },
            )
        }
    }
}

@Composable
private fun StudyRoomsApplicationTimeCard(
    text: String,
) {
    FloatingNotice(
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 30.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        text = text,
    )
}

@Composable
private fun ListStudyRooms(
    modifier: Modifier = Modifier,
    studyRooms: List<StudyRoomInformation>,
    onClick: (UUID) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp,
        ),
    ) {

        if (studyRooms.isNotEmpty()) {
            items(
                items = studyRooms,
            ) { studyRoom ->
                RoomItem(
                    roomId = studyRoom.roomId.toString(),
                    position = studyRoom.position,
                    title = studyRoom.title,
                    currentNumber = studyRoom.currentNumber,
                    maxNumber = studyRoom.maxNumber,
                    condition = studyRoom.condition,
                    isMine = studyRoom.isMine,
                ) {
                    onClick(studyRoom.roomId)
                }
            }
        } else {
            item {
                Body3(
                    text = stringResource(R.string.study_room_error_no_available_study_room),
                )
            }
        }
    }
}

// todo move to design-system layer
private val DormTimeChipShape: Shape = RoundedCornerShape(5.dp)

// todo move to design-system layer
@Composable
fun DormTimeChip(
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .background(
                color = when {
                    selected -> DormTheme.colors.primary
                    else -> Color.Transparent
                },
                shape = DormTimeChipShape,
            )
            .clip(
                shape = DormTimeChipShape,
            )
            .border(
                border = BorderStroke(
                    width = 1.dp, color = if (!selected) DormColor.Gray400
                    else Color.Transparent
                ),
                shape = DormTimeChipShape,
            )
            .dormClickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        ButtonText(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = if (selected) DormTheme.colors.onPrimary else DormColor.Gray400
        )
    }
}

private fun makeTimeRange(
    studyRoomAvailableTimeList: FetchAvailableStudyRoomTimesOutput.TimeSlotInformation,
): String = "${studyRoomAvailableTimeList.startTime} ~ ${studyRoomAvailableTimeList.endTime}"