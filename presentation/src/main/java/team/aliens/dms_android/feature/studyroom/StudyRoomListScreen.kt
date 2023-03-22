package team.aliens.dms_android.feature.studyroom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.ButtonText
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.entity.studyroom.StudyRoomAvailableTimeListEntity
import team.aliens.presentation.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StudyRoomListScreen(
    navController: NavController,
    studyRoomListViewModel: StudyRoomListViewModel = hiltViewModel(),
) {

    val toast = rememberToast()

    val studyRoomState = studyRoomListViewModel.uiState.collectAsState().value

    val studyRoomAvailableTimeList = studyRoomState.studyRoomAvailableTime

    var selectedIndex by remember { mutableStateOf(0) }

    var selectedAvailableTime by remember { mutableStateOf("") }

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

            selectedAvailableTime = setAvailableTime(studyRoomFirstEntity)
            studyRoomListViewModel.onEvent(
                event = StudyRoomListViewModel.UiEvent.SetStudyRoomAvailableTime(
                    timeSlot = studyRoomFirstEntity.id,
                )
            )
        }
    }

    var showTimeFilterDialogState by remember {
        mutableStateOf(false)
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
                    id = R.string.Check,
                ),
                btnColor = DormButtonColor.Blue,
                onBtnClick = {
                    with(studyRoomListViewModel) {
                        showTimeFilterDialogState = false
                        selectedAvailableTime =
                            setAvailableTime(studyRoomAvailableTimeList[selectedIndex])
                        onEvent(
                            event = StudyRoomListViewModel.UiEvent.SetStudyRoomAvailableTime(
                                timeSlot = studyRoomAvailableTimeList[selectedIndex].id,
                            )
                        )
                        fetchStudyRoomAvailableTimeList()
                    }
                },
            ) {

                Title3(
                    text = stringResource(R.string.Time),
                )

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
                                selected = (selectedIndex == index),
                                text = setAvailableTime(
                                    studyRoomAvailableTimeList[index],
                                ),
                                onClick = {
                                    selectedIndex = index
                                    studyRoomListViewModel.onEvent(
                                        event = StudyRoomListViewModel.UiEvent.SetStudyRoomAvailableTime(
                                            timeSlot = items.id,
                                        )
                                    )
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

            Spacer(
                modifier = Modifier.height(17.dp),
            )

            // Available study room application time
            FloatingNotice(
                content = "자습실 신청 가능 시간 : ${studyRoomState.startAt} ~ ${studyRoomState.endAt}",
            )

            Spacer(
                modifier = Modifier.height(24.dp),
            )

            // Study room time filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                // slider icon
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

                // available time
                Body3(
                    text = selectedAvailableTime,
                    color = DormColor.DormPrimary,
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp),
            )

            // List of study rooms
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                if (studyRoomState.studyRooms.isNotEmpty()) {
                    items(
                        items = studyRoomState.studyRooms,
                    ) { point ->
                        RoomItem(
                            roomId = point.roomId,
                            position = point.position,
                            title = point.title,
                            currentNumber = point.currentNumber,
                            maxNumber = point.maxNumber,
                            condition = point.condition,
                            onClick = { seatId ->
                                navController.navigate("studyRoomDetail/${seatId}")
                            },
                        )
                    }
                } else {
                    item {
                        Body3(
                            text = stringResource(R.string.NoAvailableStudyRoom),
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp),
            )
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
                    selected -> DormColor.DormPrimary
                    else -> Color.Transparent
                },
                shape = DormTimeChipShape,
            )
            .clip(
                shape = DormTimeChipShape,
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = if (!selected) DormColor.Gray400 else Color.Transparent
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
            color = if (selected) DormColor.Gray100 else DormColor.Gray400
        )
    }
}

private fun setAvailableTime(
    studyRoomAvailableTimeList: StudyRoomAvailableTimeListEntity.AvailableTime,
): String = "${studyRoomAvailableTimeList.startTime} ~ ${studyRoomAvailableTimeList.endTime}"