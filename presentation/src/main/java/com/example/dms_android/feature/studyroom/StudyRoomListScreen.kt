package com.example.dms_android.feature.studyroom

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.component.RoomContent
import com.example.design_system.toast.rememberToast
import com.example.dms_android.R
import com.example.dms_android.feature.pointlist.toNotice
import com.example.dms_android.viewmodel.mypage.MyPageViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun StudyRoomListScreen(
    navController: NavController,
    studyRoomViewModel: StudyRoomViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        studyRoomViewModel.fetchStudyRoomList()
    }

    val state = studyRoomViewModel.state.collectAsState().value
    val toast = rememberToast()
    val badRequestComment = "잘못된 요청입니다."
    val unAuthorizedComment = stringResource(id = R.string.LoginUnAuthorized)
    val forbiddenException = stringResource(id = R.string.LoginNotFound)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val unKnownException = stringResource(id = R.string.UnKnownException)

    LaunchedEffect(studyRoomViewModel) {
        studyRoomViewModel.studyRoomEffect.collect() {
            when (it) {
                is StudyRoomViewModel.Event.FetchStudyRoomList -> {
//                    point.clear()
//                    val mappingNotice = it.pointListEntity.pointValue.map { item ->
//                        item.toNotice()
//                    }
//                    point.addAll(mappingNotice.toMutableStateList())
                }
                is StudyRoomViewModel.Event.BadRequestException -> {
                    toast(badRequestComment)
                }
                is StudyRoomViewModel.Event.UnAuthorizedTokenException -> {
                    toast(unAuthorizedComment)
                }
                is StudyRoomViewModel.Event.CannotConnectException -> {
                    toast(forbiddenException)
                }
                is StudyRoomViewModel.Event.TooManyRequestException -> {
                    toast(tooManyRequestComment)
                }
                is StudyRoomViewModel.Event.InternalServerException -> {
                    toast(serverException)
                }
                is StudyRoomViewModel.Event.UnknownException -> {
                    toast(unKnownException)
                }
                is StudyRoomViewModel.Event.NullPointException -> {
                    toast("null")
                }
                else -> {
                    toast(unKnownException)
                }
            }
        }
    }

//    LazyColumn(
//        modifier = Modifier,
//    ) {
//        itemsIndexed(items = points) {
//            RoomContent(
//                roomId = "b77eafed-69ab-422d-8448-1ec1f0a2eb8c",
//                position = "2층",
//                title = "제목입니다.",
//                currentNumber = 5,
//                maxNumber = 8,
//                condition = "2학년 남자",
//                onClick = { roomId ->
//                    print(roomId)
//                }
//            )
//        }
//    }
}