package team.aliens.dms_android.feature.notice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.component.Notice
import team.aliens.design_system.component.NoticeList
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.viewmodel.notice.NoticeViewModel
import team.aliens.domain.entity.notice.NoticeListEntity
import team.aliens.domain.enums.NoticeListSCType
import team.aliens.domain.util.toDate
import team.aliens.presentation.R

fun NoticeListEntity.NoticeValue.toNotice() = Notice(
    noticeId = this.id.toString(),
    title = this.title,
    createAt = this.createAt,
)

@Composable
fun NoticeScreen(
    navController: NavController,
    noticeViewModel: NoticeViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        noticeViewModel.fetchNoticeList()
    }

    var notices = remember {
        mutableStateListOf(Notice("", "공지사항이", "없습니다."))
    }

    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.UnAuthorized)
    val forbidden = stringResource(id = R.string.Forbidden)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    LaunchedEffect(Unit) {
        noticeViewModel.noticeViewEffect.collect {
            when (it) {
                is NoticeViewModel.Event.FetchNoticeList -> {
                    notices.clear()
                    val mappingNotice = it.noticeListEntity.notices.map { item ->
                        item.toNotice()
                    }
                    for (i in 1..it.noticeListEntity.notices.size) {
                        mappingNotice[i - 1].createAt =
                            it.noticeListEntity.notices[i - 1].createAt.toDate()
                    }
                    notices.addAll(mappingNotice.toMutableStateList())
                }
                is NoticeViewModel.Event.BadRequestException -> {
                    toast(badRequestComment)
                }
                is NoticeViewModel.Event.UnAuthorizedTokenException -> {
                    toast(unAuthorizedComment)
                }
                is NoticeViewModel.Event.CannotConnectException -> {
                    toast(forbidden)
                }
                is NoticeViewModel.Event.TooManyRequestException -> {
                    toast(tooManyRequestComment)
                }
                is NoticeViewModel.Event.InternalServerException -> {
                    toast(serverException)
                }
                is NoticeViewModel.Event.UnknownException -> {
                    toast(noInternetException)
                }
                is NoticeViewModel.Event.FetchNoticeDetail -> {

                }
                is NoticeViewModel.Event.NullPointException -> {
                    toast("null")
                }
                else -> {
                    toast(noInternetException)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Body1(text = stringResource(id = R.string.Notice))
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            OrderButton(noticeViewModel)
        }
        NoticeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            notices = notices,
            onClick = { noticeId ->
                navController.navigate("noticeDetail/${noticeId}")
            },
        )
    }
}

@Composable
fun OrderButton(
    noticeViewModel: NoticeViewModel,
) {

    val context = LocalContext.current

    var text by remember {
        mutableStateOf(
            context.getString(R.string.LatestOrder),
        )
    }

    Button(
        onClick = {
            if (noticeViewModel.state.value.type == NoticeListSCType.NEW) {
                noticeViewModel.state.value.type = NoticeListSCType.OLD
                text = context.getString(R.string.LatestOrder)
                noticeViewModel.fetchNoticeList()
            } else {
                noticeViewModel.state.value.type = NoticeListSCType.NEW
                text = context.getString(R.string.OldestOrder)
                noticeViewModel.fetchNoticeList()
            }
        },
        border = BorderStroke(
            width = 1.dp,
            color = DormColor.Gray600,
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = DormColor.Gray100),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ButtonText(
                text = text,
                color = DormColor.Gray600,
            )
        }
    }
}
