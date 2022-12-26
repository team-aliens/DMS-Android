package com.example.dms_android.feature.notice

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.color.DormColor
import com.example.design_system.component.Notice
import com.example.design_system.component.NoticeList
import com.example.design_system.icon.DormIcon
import com.example.design_system.toast.rememberToast
import com.example.design_system.typography.Body4
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R
import com.example.dms_android.feature.navigator.NavigationRoute
import com.example.dms_android.viewmodel.auth.login.SignInViewModel
import com.example.dms_android.viewmodel.notice.NoticeViewModel
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType

@Composable
fun NoticeScreen(
    navController: NavController,
    noticeViewModel: NoticeViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = noticeViewModel) {
        noticeViewModel.fetchNoticeList()
    }

    val notices = listOf(
        Notice("", "공지사항이", "없습니다.")
    )

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
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200),
    ) {
        NoticeTopBar()
        NoticeOrderButton(noticeViewModel)
        NoticeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            notices = notices,
            onClick = {

            },
        )
    }
    fun NoticeListEntity.NoticeValue.toNoticeString() =
        Notice(
            noticeId = id.toString(),
            title = title,
            createAt = createAt
        )
}

fun insertNoticeValue(
    notices: List<Notice>,
    noticeListEntity: NoticeListEntity,
) {

    for (i: Int in 0 until noticeListEntity.notices.size) {

    }
}

@Composable
fun NoticeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        SubTitle2(text = stringResource(id = R.string.Notice))
    }
}

@Composable
fun NoticeOrderButton(
    noticeViewModel: NoticeViewModel
) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(backgroundColor = DormColor.Gray100),
        modifier = Modifier
            .padding(start = 20.dp)
            .border(
                color = DormColor.Gray600,
                width = 1.dp,
                shape = RoundedCornerShape(15)
            )
            .size(width = 82.dp, height = 40.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (noticeViewModel.state.value.type == NoticeListSCType.NEW) {
                            noticeViewModel.state.value.type = NoticeListSCType.OLD
                            noticeViewModel.fetchNoticeList()
                        } else {
                            noticeViewModel.state.value.type = NoticeListSCType.NEW
                            noticeViewModel.fetchNoticeList()
                        }
                    },
                text = "최신순",
            )
        }
    }
}
