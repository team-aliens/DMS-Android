package team.aliens.dms.android.feature.studyroom.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator

@Destination
@Composable
fun StudyRoomListScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    // studyRoomListViewModel: StudyRoomListViewModel = hiltViewModel(),
) {/*

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
            onPrevious = navigator::popBackStack,
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
                    navigator.openStudyRoomDetails(
                        studyRoomId = seatId,
                        timeslot = studyRoomAvailableTimeList[selectedAvailableTimeItemIndex].id,
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
    )*/
}
/*

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
*/
