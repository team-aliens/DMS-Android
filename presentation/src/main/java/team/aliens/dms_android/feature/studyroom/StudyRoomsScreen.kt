package team.aliens.dms_android.feature.studyroom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.RoomItem
import team.aliens.design_system.dialog.DormBottomAlignedContainedLargeButtonDialog
import team.aliens.design_system.dialog.DormCustomDialog
import team.aliens.design_system.extension.Space
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.constans.Extra.seatId
import team.aliens.dms_android.util.TopBar
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.presentation.R
import java.util.UUID

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StudyRoomsScreen(
    navController: NavController,
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
            .background(
                DormTheme.colors.background,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TopBar(
            title = stringResource(
                id = R.string.ApplicateStudyRoom,
            ),
        ) {
            navController.popBackStack()
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Space(space = 17.dp)

            AnimatedVisibility(
                visible = studyRoomState.hasApplyTime,
            ) {
                FloatingNotice(
                    content = stringResource(
                        id = R.string.study_room_apply_time,
                        "${studyRoomState.startAt} ~ ${studyRoomState.endAt}"
                    )
                )
            }

            Space(space = 24.dp)

            if (studyRoomAvailableTimeList.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {

                    Image(
                        modifier = Modifier
                            .size(24.dp)
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

            Space(space = 24.dp)

            ListStudyRooms(studyRooms = studyRoomState.studyRooms, onClick = { seatId: UUID ->
                navController.navigate("studyRoomDetails/${seatId}/${studyRoomAvailableTimeList[selectedAvailableTimeItemIndex].id}")
            })
        }
        Space(space = 24.dp)
    }
}

@Composable
private fun ListStudyRooms(
    studyRooms: List<StudyRoomInformation>,
    onClick: (UUID) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
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
                    width = 1.dp, color = if (!selected) DormColor.Gray400 else Color.Transparent
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