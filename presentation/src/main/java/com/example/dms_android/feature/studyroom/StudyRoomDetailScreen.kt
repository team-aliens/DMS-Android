package com.example.dms_android.feature.studyroom

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.button.DormButtonColor
import com.example.design_system.button.DormContainedLargeButton
import com.example.design_system.color.DormColor
import com.example.design_system.component.RoomContent
import com.example.design_system.component.RoomDetail
import com.example.design_system.component.SeatItem
import com.example.design_system.component.SeatType
import com.example.design_system.component.SeatTypeList
import com.example.design_system.component.SeatTypeUiModel
import com.example.design_system.toast.rememberToast
import com.example.design_system.typography.Body5
import com.example.dms_android.R
import com.example.dms_android.util.TopBar
import com.example.domain.entity.studyroom.SeatTypeEntity
import com.example.domain.entity.studyroom.StudyRoomDetailEntity

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
    studyRoomViewModel: StudyRoomViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        studyRoomViewModel.fetchStudyRoomDetail(roomId)
        studyRoomViewModel.fetchApplyTime()
        studyRoomViewModel.fetchRoomSeatType()
    }

    val state = studyRoomViewModel.state.collectAsState().value

    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.UnAuthorized)
    val forbidden = stringResource(id = R.string.Forbidden)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    LaunchedEffect(Unit) {
        studyRoomViewModel.studyRoomDetailEffect.collect {
            when (it) {
                is StudyRoomViewModel.Event.FetchStudyDetail -> {

                }
                is StudyRoomViewModel.Event.BadRequestException -> {
                    toast(badRequestComment)
                }
                is StudyRoomViewModel.Event.UnAuthorizedTokenException -> {
                    toast(unAuthorizedComment)
                }
                is StudyRoomViewModel.Event.CannotConnectException -> {
                    toast(forbidden)
                }
                is StudyRoomViewModel.Event.TooManyRequestException -> {
                    toast(tooManyRequestComment)
                }
                is StudyRoomViewModel.Event.InternalServerException -> {
                    toast(serverException)
                }
                is StudyRoomViewModel.Event.UnknownException -> {
                    toast(noInternetException)
                }
                else -> {
                    toast("알 수 없는 에러가 발생했습니다.")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        studyRoomViewModel.studyRoomApplyEffect.collect {
            Log.d("123", "svd")
            when (it) {
                is StudyRoomViewModel.Event.ApplyStudyRoom -> {
                    toast("자습실 신청에 성공하셨습니다.")
                    studyRoomViewModel.fetchStudyRoomDetail(roomId)
                }
                is StudyRoomViewModel.Event.CancelStudyRoom -> {
                    toast("자습실 신청을 취소하셨습니다.")
                    studyRoomViewModel.fetchStudyRoomDetail(roomId)
                }
                is StudyRoomViewModel.Event.BadRequestException -> {
                    toast(badRequestComment)
                }
                is StudyRoomViewModel.Event.UnAuthorizedTokenException -> {
                    toast(unAuthorizedComment)
                }
                is StudyRoomViewModel.Event.CannotConnectException -> {
                    toast(forbidden)
                }
                is StudyRoomViewModel.Event.ConflictException -> {
                    toast("해당 자리로 이미 예약 되었습니다.")
                }
                is StudyRoomViewModel.Event.TooManyRequestException -> {
                    toast(tooManyRequestComment)
                }
                is StudyRoomViewModel.Event.InternalServerException -> {
                    toast(serverException)
                }
                is StudyRoomViewModel.Event.UnknownException -> {
                    toast(noInternetException)
                }
                else -> {
                    toast("알 수 없는 에러가 발생했습니다.")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DormColor.Gray100
            )
    ) {
        TopBar(
            title = "자습실 신청",
        ) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = DormColor.Gray200
                )
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(17.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(22.dp),
                    )
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 2.dp),
                    painter = painterResource(id = R.drawable.coloricnotice),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(12.dp))
                Body5(
                    text = "자습실 신청 가능 시간: ${state.startAt} ~ ${state.endAt}"
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            RoomContent(
                roomId = "",
                position = "${state.roomDetail.floor}층",
                title = state.roomDetail.name,
                currentNumber = state.roomDetail.inUseHeadCount,
                maxNumber = state.roomDetail.totalAvailableSeat,
                isMine = false,
                condition = "${state.roomDetail.availableGrade}${stringResource(id = R.string.grade)} ${state.roomDetail.studyRoomSex}",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .background(
                        color = DormColor.Gray100
                    )
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                SeatTypeList(items = state.seatTypeListEntity.types.map { it.toModel() })
                Spacer(modifier = Modifier.height(25.dp))
                RoomDetail(
                    topDescription = state.roomDetail.northDescription,
                    bottomDescription = state.roomDetail.southDescription,
                    startDescription = state.roomDetail.westDescription,
                    endDescription = state.roomDetail.eastDescription,
                    seats = state.roomDetail.toDesignSystemModel(),
                    selected = state.currentSeat ?: "",
                    onSelectedChanged = { seatId ->
                        studyRoomViewModel.updateCurrentSeat(seatId)
                    },
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    DormContainedLargeButton(
                        modifier = Modifier.fillMaxWidth(0.48f),
                        text = "취소",
                        color = DormButtonColor.Gray,
                    ) {
                        studyRoomViewModel.cancelStudyRoomSeat()
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    DormContainedLargeButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "신청",
                        color = DormButtonColor.Blue,
                    ) {
                        if (state.currentSeat == null) {
                            toast("자리를 먼저 선택해주세요.")
                        } else {
                            studyRoomViewModel.applyStudyRoomSeat(state.currentSeat)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))   
            }
        }
    }
}


fun SeatTypeEntity.Type.toModel() =
    SeatTypeUiModel(
        color = color,
        text = name
    )

private fun StudyRoomDetailEntity.toDesignSystemModel(): List<List<SeatItem>> {
    val defaultSeats: MutableList<MutableList<SeatItem>> = List(
        size = this.totalHeightSize,
        init = {
            List(
                size = this.totalWidthSize,
                init = {
                    SeatItem()
                }
            ).toMutableList()
        }
    ).toMutableList()

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