package com.example.dms_android.feature.studyroom

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.color.DormColor
import com.example.design_system.component.RoomContent
import com.example.design_system.component.RoomDetail
import com.example.design_system.component.SeatItem
import com.example.design_system.toast.rememberToast
import com.example.dms_android.R
import com.example.dms_android.util.observeWithLifecycle
import com.example.domain.entity.studyroom.StudyRoomDetailEntity

@SuppressLint("ResourceType")
@Composable
fun StudyRoomDetailScreen(
    navController: NavController,
    roomId: String,
    vm: StudyRoomViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        vm.fetchStudyRoomDetail(roomId)
    }

    val state = vm.state.collectAsState().value

    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.UnAuthorized)
    val forbidden = stringResource(id = R.string.Forbidden)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    vm.studyRoomDetailEffect.observeWithLifecycle { sideEffect ->
        when (sideEffect) {
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

    Column {
        RoomContent(
            roomId = "",
            position = "${state.roomDetail.floor}층",
            title = state.roomDetail.name,
            currentNumber = state.roomDetail.inUseHeadCount,
            maxNumber = state.roomDetail.totalAvailableSeat,
            condition = "${state.roomDetail.availableGrade}학년 ${state.roomDetail.studyRoomSex}",
            onClick = { }
        )
        RoomDetail(
            topDescription = state.roomDetail.northDescription,
            bottomDescription = state.roomDetail.southDescription,
            startDescription = state.roomDetail.westDescription,
            endDescription = state.roomDetail.eastDescription,
            seats = state.roomDetail.toDesignSystemModel(),
            onClick = { seatId ->
                print(seatId)
            },
        )
    }
}

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
        val width = seat.widthLocation
        val height = seat.heightLocation
        val color = seat.type?.color

        defaultSeats[height][width] = SeatItem(
            id = seat.id,
            number = seat.number,
            name = seat.type?.name,
            color = if(color != null) {
                getColor(color)
            } else DormColor.DormPrimary,
        )
    }

    return defaultSeats
}
