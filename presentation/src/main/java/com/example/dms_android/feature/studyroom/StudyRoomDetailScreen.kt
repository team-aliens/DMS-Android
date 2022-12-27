package com.example.dms_android.feature.studyroom

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.color.DormColor
import com.example.design_system.component.RoomContent
import com.example.design_system.component.RoomDetail
import com.example.design_system.component.SeatItem
import com.example.design_system.toast.rememberToast
import com.example.dms_android.R
import kotlin.random.Random
import com.example.domain.entity.studyroom.StudyRoomDetailEntity

@SuppressLint("ResourceType")
@Composable
fun StudyRoomDetailScreen(
    navController: NavController,
    roomId: String,
    studyRoomViewModel: StudyRoomViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        studyRoomViewModel.fetchStudyRoomDetail(roomId)
    }

    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.UnAuthorized)
    val forbidden = stringResource(id = R.string.Forbidden)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    var studyROomDetailEntity: StudyRoomDetailEntity =
        com.example.domain.entity.studyroom.StudyRoomDetailEntity(
            floor = 0,
            name = "",
            totalAvailableSeat = 0,
            inUseHeadCount = 0,
            availableSex = "",
            availableGrade = 0,
            eastDescription = "",
            westDescription = "",
            southDescription = "",
            northDescription = "",
            totalWidthSize = 0,
            totalHeightSize = 0,
            studyRoomSex = "",
            seats = listOf(
                StudyRoomDetailEntity.Seat(
                    id = "",
                    widthLocation = 0,
                    heightLocation = 0,
                    number = 0,
                    type = StudyRoomDetailEntity.Type(
                        id = "",
                        name = "",
                        color = "",
                    ),
                    status = "",
                    isMine = false,
                    student = StudyRoomDetailEntity.Student(
                        id = "",
                        name = ""
                    )
                )
            )
        )


    LaunchedEffect(Unit) {
        studyRoomViewModel.studyRoomDetailEffect.collect {
            when (it) {
                is StudyRoomViewModel.Event.FetchStudyDetail -> {
                    studyROomDetailEntity = it.studyRoomDetailEntity
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
                    toast("aewfawefaefawe")
                }
            }
        }
    }

    var heightMax = 0
    var widthMax = 0

    val DummySeat = (0..20).map { a ->
        (0..20).map { b ->
            val random = Random.nextInt(3)
            when (random) {
                0 -> SeatItem(
                    id = (a + b).toString(),
                    name = "유저${a + b}",
                    color = DormColor.DormPrimary
                )
                1 -> SeatItem(id = (a + b).toString(), name = null, color = DormColor.Lighten100)
                else -> SeatItem(id = null, name = null)
            }
        }.toList()
    }.toList()
//    for (i in 0 until studyRoomDetailEntity?.seats?.size!!) {
//        if (studyRoomDetailEntity!!.seats[i].heightLocation > heightMax) {
//            heightMax = studyRoomDetailEntity!!.seats[i].heightLocation
//        }
//        if (studyRoomDetailEntity!!.seats[i].widthLocation > widthMax) {
//            widthMax = studyRoomDetailEntity!!.seats[i].widthLocation
//        }
//    }
    val dummySeat = (0..heightMax).map { a ->
        (0..widthMax).map { b ->
//            SeatItem(
//                id = studyRoomDetailEntity!!.seats[i].id.toString(),
//                name = studyRoomDetailEntity!!.seats[i].student?.name,
//                color = colorResource(studyRoomDetailEntity!!.seats[i].type?.color!!.toColorInt())
//            )
        }.toList()
    }.toList()
    Column() {
        RoomContent(
            roomId = "",
            position = "${studyROomDetailEntity.floor}층",
            title = studyROomDetailEntity.name,
            currentNumber = studyROomDetailEntity.inUseHeadCount,
            maxNumber = studyROomDetailEntity.totalAvailableSeat,
            condition = "${studyROomDetailEntity.availableGrade}학년 $studyROomDetailEntity.studyRoomSex",
            onClick = { }
        )
        RoomDetail(
            topDescription = studyROomDetailEntity.northDescription,
            bottomDescription = studyROomDetailEntity.southDescription,
            startDescription = studyROomDetailEntity.westDescription,
            endDescription = studyROomDetailEntity.eastDescription,
            seats = DummySeat,
            onClick = { seatId ->
                print(seatId)
            },
        )
    }
}