package team.aliens.dms.android.feature.studyroom.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.Button
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.ModalBottomSheet
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.VerticallyFadedLazyColumn
import team.aliens.dms.android.core.ui.DefaultHorizontalSpace
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.composable.FloatingNotice
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.startPadding
import team.aliens.dms.android.data.studyroom.model.AvailableStudyRoomTime
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.studyroom.StudyRoomCard
import team.aliens.dms.android.feature.studyroom.navigation.StudyRoomNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun StudyRoomListScreen(
    modifier: Modifier = Modifier,
    navigator: StudyRoomNavigator,
    viewModel: StudyRoomListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val (shouldShowTimeFilterBottomSheet, onShouldShowTimeFilterBottomSheetChange) = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    if (shouldShowTimeFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onShouldShowTimeFilterBottomSheetChange(false) },
            sheetState = sheetState,
        ) {
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
                                        viewModel.postIntent(
                                            StudyRoomListIntent.UpdateSelectedAvailableStudyRoomTime(
                                                availableStudyRoomTime,
                                            ),
                                        )
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
                        scope.launch { sheetState.hide() }
                    },
                ) {
                    Text(text = stringResource(id = R.string.accept))
                }
            }
        }
    }

    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == SheetValue.Hidden) {
            onShouldShowTimeFilterBottomSheetChange(false)
        }
    }

    Scaffold(
        modifier = modifier,
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
                onFilterButtonClick = { onShouldShowTimeFilterBottomSheetChange(true) },
            )
            uiState.studyRooms?.let { studyRooms ->
                VerticallyFadedLazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(studyRooms) { studyRoom ->
                        StudyRoomCard(
                            modifier = Modifier.fillMaxWidth(),
                            studyRoom = studyRoom,
                            onClick = {
                                navigator.openStudyRoomDetails(
                                    studyRoomId = studyRoom.id,
                                    studyRoomName = studyRoom.name,
                                    timeslot = uiState.selectedAvailableStudyRoomTime!!.id,
                                    studyRoomApplicationStartTime = uiState.studyRoomApplicationTime!!.startAt,
                                    studyRoomApplicationEndTime = uiState.studyRoomApplicationTime!!.endAt,
                                )
                            },
                        )
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
