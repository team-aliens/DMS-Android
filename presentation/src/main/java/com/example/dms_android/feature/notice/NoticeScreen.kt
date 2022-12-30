package com.example.dms_android.feature.notice

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.color.DormColor
import com.example.design_system.component.Notice
import com.example.design_system.component.NoticeList
import com.example.design_system.toast.rememberToast
import com.example.design_system.typography.Body1
import com.example.design_system.typography.Body2
import com.example.design_system.typography.SubTitle2
import com.example.dms_android.R
import com.example.dms_android.feature.navigator.NavigationRoute
import com.example.dms_android.viewmodel.notice.NoticeViewModel
import com.example.domain.entity.notice.NoticeListEntity
import com.example.domain.enums.NoticeListSCType
import com.example.domain.util.toDate

fun NoticeListEntity.NoticeValue.toNotice() =
    Notice(
        noticeId = this.id.toString(),
        title = this.title,
        createAt = this.createAt,
    )

@Composable
fun NoticeScreen(
    navController: NavController,
    noticeViewModel: NoticeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        noticeViewModel.fetchNoticeList()
    }

    var notices = remember {
        mutableStateListOf(
            Notice(
                "", "공지사항이", "없습니다."
            )
        )
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
                        mappingNotice[i - 1].createAt = it.noticeListEntity.notices[i - 1].createAt.toDate()
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
                    toast("dw")
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
            .background(DormColor.Gray200),
    ) {
        NoticeTopBar()
        NoticeOrderButton(noticeViewModel)
        NoticeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            notices = notices,
            onClick = { noticeId ->
                navController.navigate("noticeDetail/${noticeId}")
            },
        )
    }
}

@Composable
fun NoticeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Body1(text = "자습실 신청")
    }
}

@Composable
fun NoticeOrderButton(
    noticeViewModel: NoticeViewModel
) {
    var text by remember {
        mutableStateOf("최신순")
    }
    Button(
        onClick = {
            if (noticeViewModel.state.value.type == NoticeListSCType.NEW) {
                noticeViewModel.state.value.type = NoticeListSCType.OLD
                text = "오래된순"
                noticeViewModel.fetchNoticeList()
            } else {
                noticeViewModel.state.value.type = NoticeListSCType.NEW
                text = "최신순"
                noticeViewModel.fetchNoticeList()
            }
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = DormColor.Gray100),
        modifier = Modifier
            .padding(start = 20.dp)
            .border(
                color = DormColor.Gray600,
                width = 1.dp,
                shape = RoundedCornerShape(15)
            )
            .size(width = 88.dp, height = 40.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
            )
        }
    }
}
