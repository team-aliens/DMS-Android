package team.aliens.dms_android.feature.studyroom

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.RoomContent
import team.aliens.design_system.toast.rememberToast
import team.aliens.dms_android.component.FloatingNotice
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.presentation.R

data class RoomDataClass(
    val roomId: String,
    val position: String,
    val title: String,
    val currentNumber: Int,
    val isMine: Boolean,
    val maxNumber: Int,
    val condition: String,
)

private fun StudyRoomListEntity.StudyRoom.toNotice() = RoomDataClass(
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

    val toast = rememberToast()

    val context = LocalContext.current

    val studyRooms = remember {
        mutableStateListOf<RoomDataClass>()
    }

    LaunchedEffect(studyRoomViewModel) {

        studyRoomViewModel.fetchStudyRoomList()

        studyRoomViewModel.studyRoomEffect.collect() { event ->
            when (event) {
                is StudyRoomViewModel.Event.FetchStudyRoomList -> {

                    val mappedStudyRoom = event.studyRoomListEntity.studyRooms.map {
                        it.toNotice()
                    }

                    studyRooms.addAll(
                        mappedStudyRoom.toMutableStateList(),
                    )
                }
                else -> {
                    toast(
                        getStringFromEvent(
                            context = context,
                            event = event,
                        ),
                    )
                }
            }
        }
    }

    val studyRoomState = studyRoomViewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TopBar(
            title = stringResource(
                id = R.string.ApplicateStudyRoom,
            ),
        ) {
            navController.popBackStack()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DormColor.Gray200)
                .padding(horizontal = 16.dp),
        ) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                item {
                    Spacer(
                        modifier = Modifier.height(79.dp),
                    )
                }

                items(
                    items = studyRooms,
                ) { point ->
                    RoomContent(
                        roomId = point.roomId,
                        position = point.position,
                        title = point.title,
                        currentNumber = point.currentNumber,
                        maxNumber = point.maxNumber,
                        condition = point.condition,
                        isMine = point.isMine,
                        onClick = { seatId ->
                            navController.navigate("studyRoomDetail/${seatId}")
                        },
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(12.dp),
                    )
                }
            }

            Column() {

                Spacer(modifier = Modifier.height(17.dp))

                FloatingNotice(
                    content = "자습실 신청 가능 시간: ${studyRoomState.startAt} ~ ${studyRoomState.endAt}",
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private fun getStringFromEvent(
    context: Context,
    event: StudyRoomViewModel.Event,
): String {
    return context.getString(
        when (event) {
            StudyRoomViewModel.Event.BadRequestException -> R.string.BadRequest
            StudyRoomViewModel.Event.CannotConnectException -> R.string.NoInternetException
            StudyRoomViewModel.Event.InternalServerException -> R.string.ServerException
            StudyRoomViewModel.Event.NotFoundException -> R.string.NotFound
            StudyRoomViewModel.Event.TooManyRequestException -> R.string.TooManyRequest
            StudyRoomViewModel.Event.UnAuthorizedTokenException -> R.string.UnAuthorized
            StudyRoomViewModel.Event.UnknownException -> R.string.UnKnownException
            else -> throw IllegalArgumentException()
        },
    )
}
