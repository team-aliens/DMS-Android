package com.example.dms_android.feature.notice

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.color.DormColor
import com.example.design_system.component.Notice
import com.example.design_system.toast.rememberToast
import com.example.design_system.typography.Body5
import com.example.design_system.typography.Caption
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R
import com.example.dms_android.util.TopBar
import com.example.dms_android.viewmodel.notice.NoticeViewModel

@Composable
fun NoticeDetailScreen(noticeViewModel: NoticeViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = noticeViewModel) {
        // NoticeViewModel.fetchNoticeDetail(noticeId)
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
                is NoticeViewModel.Event.FetchNoticeDetail -> {

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
        TopBar(title = stringResource(id = R.string.Notice))
        NoticeDetailValue()
    }
}

@Composable
fun NoticeDetailValue() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 23.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Spacer(
            modifier = Modifier
                .height(55.dp),
        )
        SubTitle2(
            text = stringResource(id = R.string.NoticeTitle),
        )
        Spacer(
            modifier = Modifier
                .height(25.dp),
        )
        Caption(text = stringResource(id = R.string.NoticeTime))
        Spacer(
            modifier = Modifier
                .height(20.dp),
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(end = 23.dp)
                .background(DormColor.Gray300),
        )
        Spacer(
            modifier = Modifier
                .height(20.dp),
        )
        Box(
            modifier = Modifier
                .padding(end = 23.dp),
        ) {
            Body5(
                text = stringResource(id = R.string.NoticeContent),
            )
        }
    }
}

@Preview
@Composable
fun NoticeDetailPreView() {
    NoticeDetailScreen()
}