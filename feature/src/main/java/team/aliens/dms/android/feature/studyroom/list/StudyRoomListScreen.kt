package team.aliens.dms.android.feature.studyroom.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.DmsBottomSheetScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.ShadowDefaults
import team.aliens.dms.android.core.designsystem.layout.VerticallyFadedLazyColumn
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.endPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.studyroom.model.AvailableStudyRoomTime
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator
import team.aliens.dms.android.shared.model.Sex

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun StudyRoomListScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    viewModel: StudyRoomListViewModel = hiltViewModel(),
) {
    // TODO: val toastState = rememberToastState()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = SheetValue.Hidden,
        ),
    )

    var newlySelectedAvailableStudyRoomTime by remember {
        mutableStateOf<AvailableStudyRoomTime?>(null)
    }

    DmsBottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = false,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
            ) {
                Text(
                    modifier = Modifier.startPadding(),
                    text = stringResource(id = R.string.study_room_time),
                    style = DmsTheme.typography.title2,
                )
                uiState.availableStudyRoomTimes?.let { availableStudyRoomTimes ->
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
                        contentPadding = PaddingDefaults.Horizontal,
                    ) {
                        items(availableStudyRoomTimes) { availableStudyRoomTime ->
                            val selected =
                                availableStudyRoomTime == uiState.selectedAvailableStudyRoomTime
                            if (selected) {
                                Button(
                                    onClick = { /* explicit blank */ },
                                ) {
                                    Text(
                                        text = stringResource(
                                            id = R.string.format_study_room_available_study_room_time,
                                            availableStudyRoomTime.startTime,
                                            availableStudyRoomTime.endTime,
                                        ),
                                    )
                                }
                            } else {
                                OutlinedButton(
                                    onClick = {
                                        newlySelectedAvailableStudyRoomTime = availableStudyRoomTime
                                    },
                                    colors = ButtonDefaults.outlinedGrayButtonColors(),
                                ) {
                                    Text(
                                        text = stringResource(
                                            id = R.string.format_study_room_available_study_room_time,
                                            availableStudyRoomTime.startTime,
                                            availableStudyRoomTime.endTime,
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    onClick = {
                        if (newlySelectedAvailableStudyRoomTime != null) {
                            if (newlySelectedAvailableStudyRoomTime != uiState.selectedAvailableStudyRoomTime) {
                                viewModel.postIntent(
                                    StudyRoomListIntent.UpdateSelectedAvailableStudyRoomTime(
                                        availableStudyRoomTime = newlySelectedAvailableStudyRoomTime!!,
                                    ),
                                )
                            }
                        }
                        scope.launch { scaffoldState.bottomSheetState.hide() }
                    },
                ) {
                    Text(text = stringResource(id = R.string.accept))
                }
                Spacer(modifier = Modifier.height(52.dp))
            }
        },
        topBar = {
            DmsTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.study_room_application))
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .padding(padValues)
                .fillMaxSize(),
        ) {
            uiState.studyRoomApplicationTime?.let { studyRoomApplicationTime ->
                FloatingNotice(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    text = stringResource(
                        id = R.string.format_study_room_application_time,
                        studyRoomApplicationTime.startAt,
                        studyRoomApplicationTime.endAt,
                    ),
                )
            }
            AvailableStudyRoomTimeFilter(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                availableStudyRoomTime = uiState.selectedAvailableStudyRoomTime,
                onFilterButtonClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                },
            )
            uiState.studyRooms?.let { studyRooms ->
                VerticallyFadedLazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // tODO: remove
                    repeat(5) {
                        items(studyRooms) { studyRoom ->
                            StudyRoomCard(
                                modifier = Modifier.fillMaxWidth(),
                                studyRoom = studyRoom,
                                onClick = {
                                    navigator.openStudyRoomDetails(
                                        studyRoomId = studyRoom.id,
                                        studyRoomName = studyRoom.name,
                                        timeslot = uiState.selectedAvailableStudyRoomTime!!.id,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AvailableStudyRoomTimeFilter(
    modifier: Modifier = Modifier,
    availableStudyRoomTime: AvailableStudyRoomTime?,
    onFilterButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onFilterButtonClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                tint = DmsTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
        availableStudyRoomTime?.let {
            Text(
                text = stringResource(
                    id = R.string.format_study_room_available_study_room_time,
                    it.startTime,
                    it.endTime,
                ),
                color = DmsTheme.colorScheme.primary,
                style = DmsTheme.typography.button,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudyRoomCard(
    modifier: Modifier = Modifier,
    studyRoom: StudyRoom,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .horizontalPadding()
            .verticalPadding(PaddingDefaults.ExtraSmall),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = ShadowDefaults.SmallElevation),
        onClick = onClick,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DefaultHorizontalSpace),
            ) {
                Text(
                    modifier = Modifier
                        .startPadding()
                        .topPadding(),
                    text = stringResource(
                        id = R.string.format_study_room_floor,
                        studyRoom.floor,
                    ),
                    style = DmsTheme.typography.body2,
                    color = DmsTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier
                        .topPadding()
                        .weight(1f),
                    text = studyRoom.name,
                    style = DmsTheme.typography.body2,
                    color = DmsTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier
                        .topPadding()
                        .endPadding(),
                    text = stringResource(
                        id = R.string.format_study_room_headcount,
                        studyRoom.inUseHeadcount,
                        studyRoom.totalAvailableSeat,
                    ),
                    style = DmsTheme.typography.body2,
                    color = DmsTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                modifier = Modifier
                    .horizontalPadding()
                    .verticalPadding(),
                text = stringResource(
                    id = R.string.format_study_room_available_for,
                    studyRoom.availableGrade,
                    studyRoom.availableSex.text,
                ),
                style = DmsTheme.typography.body2,
                color = DmsTheme.colorScheme.primary,
            )
        }
    }
}

@Stable
private val Sex.text: String
    @Composable get() = stringResource(
        id = when (this) {
            Sex.MALE -> R.string.sex_male
            Sex.FEMALE -> R.string.sex_female
            Sex.ALL -> R.string.sex_all
        },
    )
