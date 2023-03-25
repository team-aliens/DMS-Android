package team.aliens.dms_android.feature.studyroom

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.util.*
import kotlinx.coroutines.launch
import team.aliens.design_system.button.DormButtonColor
import team.aliens.design_system.button.DormContainedLargeButton
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.*
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.studyroom.StudyRoomDetailsViewModel
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.presentation.R

/**
 * 자습실 상세보기 screen
 *
 * @param roomId 자습실 아이디
 */
@SuppressLint("ResourceType")
@Composable
fun StudyRoomDetailScreen(
    navController: NavController,
    roomId: String,
    timeSlot: UUID,
    studyRoomDetailsViewModel: StudyRoomDetailsViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        studyRoomDetailsViewModel.initStudyRoom(
            roomId = roomId,
            timeSlot = timeSlot,
        )
    }

    val context = LocalContext.current
    val toast = rememberToast()
    val scope = rememberCoroutineScope()

    val uiState = studyRoomDetailsViewModel.uiState.collectAsState().value
    val currentSeat = uiState.currentSeat.collectAsState("").value

    fun SeatTypeEntity.Type.toModel() = SeatTypeUiModel(
        color = color,
        text = name,
    )

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
            title = stringResource(
                id = R.string.ApplicateStudyRoom,
            ),
        ) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                ),
        ) {

            Spacer(
                modifier = Modifier.height(17.dp),
            )


            FloatingNotice(
                content = "자습실 신청 가능 시간 : ${uiState.startAt} ~ ${uiState.endAt}",
            )


            Spacer(
                modifier = Modifier.height(24.dp),
            )


            RoomItem(
                roomId = "",
                position = "${uiState.studyRoomDetails.floor}층",
                title = uiState.studyRoomDetails.name,
                currentNumber = uiState.studyRoomDetails.inUseHeadCount,
                maxNumber = uiState.studyRoomDetails.totalAvailableSeat,
                condition = uiState.studyRoomDetails.availableGrade + stringResource(
                    id = R.string.Grade,
                ) + " ${uiState.studyRoomDetails.studyRoomSex}",
                onClick = { },
            )


            Spacer(
                modifier = Modifier.height(15.dp),
            )

            if (uiState.seatBoolean) {
                SeatTypeList(items = uiState.seatType.types.map { it.toModel() })
            }
            Spacer(
                modifier = Modifier.height(15.dp),
            )


            RoomDetail(
                topDescription = uiState.studyRoomDetails.northDescription,
                bottomDescription = uiState.studyRoomDetails.southDescription,
                startDescription = uiState.studyRoomDetails.westDescription,
                endDescription = uiState.studyRoomDetails.eastDescription,
                seats = uiState.studyRoomDetails.toDesignSystemModel(),
                selected = currentSeat,
            ) { seat ->
                studyRoomDetailsViewModel.onEvent(
                    event = StudyRoomDetailsViewModel.UiEvent.ChangeSelectedSeat(
                        seat = seat,
                    ),
                )
            }


            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                // Cancel button
                DormContainedLargeButton(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    text = stringResource(
                        id = R.string.Cancel,
                    ),
                    color = DormButtonColor.Gray,
                ) {
                    studyRoomDetailsViewModel.onEvent(
                        event = StudyRoomDetailsViewModel.UiEvent.CancelApplySeat,
                    )
                }


                Spacer(
                    modifier = Modifier.width(10.dp),
                )


                // Apply button
                DormContainedLargeButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        id = R.string.Application,
                    ),
                    color = DormButtonColor.Blue,
                ) {
                    scope.launch {
                        if (currentSeat.isEmpty()) {
                            return@launch toast(
                                context.getString(R.string.PleaseSelectSeatFirst),
                            )
                        }
                        studyRoomDetailsViewModel.onEvent(
                            event = StudyRoomDetailsViewModel.UiEvent.ApplySeat(
                                seat = currentSeat,
                                timeSlot = timeSlot,
                            )
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(54.dp),
            )
        }
    }
}

/**
 * [StudyRoomDetailEntity] 를 디자인 시스템인 [RoomDetail] 에서 사용하기 위해
 * List<List<SeatItem>> 형식으로 Mapping 에주는 extension
 *
 * @return 디자인 시스템에서 사용할 수 있는 List<List<SeatItem>> 형식
 */
private fun StudyRoomDetailEntity.toDesignSystemModel(): List<List<SeatItem>> {
    val defaultSeats: MutableList<MutableList<SeatItem>> =
        List(size = this.totalHeightSize, init = {
            List(size = this.totalWidthSize, init = {
                SeatItem()
            }).toMutableList()
        }).toMutableList()

    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }

    this.seats.map { seat ->
        val width = seat.widthLocation - 1
        val height = seat.heightLocation - 1
        val color = seat.type?.color

        defaultSeats[height][width] = SeatItem(
            id = seat.id,
            number = seat.number,
            name = seat.student?.name,
            color = if (color != null) {
                getColor(color)
            } else DormColor.DormPrimary,
            type = SeatType.toSeatType(seat.status)
        )
    }

    return defaultSeats
}
