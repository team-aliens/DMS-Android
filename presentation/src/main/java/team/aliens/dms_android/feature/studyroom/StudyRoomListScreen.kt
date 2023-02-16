package team.aliens.dms_android.feature.studyroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.RoomContent
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body5
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.presentation.R

fun StudyRoomListEntity.StudyRoom.toNotice() = RoomDataClass(
    roomId = id,
    position = "${floor}층",
    title = name,
    currentNumber = inUseHeadcount,
    isMine = isMine,
    maxNumber = totalAvailableSeat,
    condition = "${availableGrade}학년 $studyRoomSex",
)

@Composable
fun StudyRoomListScreen(
    navController: NavController,
    studyRoomViewModel: StudyRoomViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        studyRoomViewModel.fetchStudyRoomList()
        studyRoomViewModel.fetchApplyTime()
    }

    var sex by remember {
        mutableStateOf("")
    }

    var points = remember {
        mutableStateListOf(RoomDataClass(
            roomId = "qewf",
            position = "qwef",
            title = "qwef",
            currentNumber = 1,
            maxNumber = 2,
            isMine = false,
            condition = "afwe",
        ),
            RoomDataClass(
                roomId = "qewf",
                position = "qwef",
                title = "qwef",
                currentNumber = 1,
                maxNumber = 2,
                isMine = false,
                condition = "afwe",
            ))
    }

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
                    points.clear()
                    val mappingStudyRoom = it.studyRoomListEntity.studyRooms.map { item ->
                        item.toNotice()
                    }
                    points.addAll(mappingStudyRoom.toMutableStateList())
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
    val state = studyRoomViewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(title = "자습실 신청"){
            navController.popBackStack()
        }
        Spacer(modifier = Modifier.height(27.dp))
        Box(contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 20.dp)
                .dormShadow(
                    color = DormColor.Gray500,
                    offsetY = 1.dp,
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100),
                )) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(26.dp),
                    painter = painterResource(id = R.drawable.coloricnotice),
                    contentDescription = stringResource(id = R.string.icNotice),
                )
                Spacer(modifier = Modifier.width(13.dp))
                Body5(text = "자습실 신청 가능 시간: ${state.startAt} ~ ${state.endAt}")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .background(DormColor.Gray200),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(items = points) { point ->
                RoomContent(roomId = point.roomId,
                    position = point.position,
                    title = point.title,
                    currentNumber = point.currentNumber,
                    maxNumber = point.maxNumber,
                    condition = point.condition,
                    isMine = point.isMine,
                    onClick = { seatId ->
                        navController.navigate("studyRoomDetail/${seatId}")
                    })
            }
        }
    }
}

data class RoomDataClass(
    val roomId: String,
    val position: String,
    val title: String,
    val currentNumber: Int,
    val isMine: Boolean,
    val maxNumber: Int,
    val condition: String,
)