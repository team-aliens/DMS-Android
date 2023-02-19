package team.aliens.dms_android.feature.notice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.SubTitle2
import team.aliens.dms_android.util.TopBar
import team.aliens.dms_android.viewmodel.notice.NoticeViewModel
import team.aliens.domain.util.toDate
import team.aliens.presentation.R

@SuppressLint("RememberReturnType")
@Composable
fun NoticeDetailScreen(
    navController: NavController,
    noticeId: String,
    noticeViewModel: NoticeViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = noticeViewModel) {
        noticeViewModel.fetchNoticeDetail(noticeId)
    }
    val toast = rememberToast()

    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.UnAuthorized)
    val forbidden = stringResource(id = R.string.Forbidden)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    val state = noticeViewModel.state.collectAsState().value.noticeDetail

    LaunchedEffect(Unit) {
        noticeViewModel.noticeDetailViewEffect.collect {
            when (it) {
                is NoticeViewModel.Event.FetchNoticeDetail -> {
                    state.createAt = state.createAt.toDate()
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
                else -> {

                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray200)
            .verticalScroll(rememberScrollState()),
    ) {
        TopBar(title = stringResource(id = R.string.Announcement)) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier
                .background(DormColor.Gray200)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(
                modifier = Modifier.height(55.dp),
            )
            SubTitle2(
                text = state.title,
            )
            Spacer(
                modifier = Modifier.height(25.dp),
            )
            Caption(
                text = state.createAt,
                color = DormColor.Gray500,
            )
            Spacer(
                modifier = Modifier.height(20.dp),
            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(DormColor.Gray300),
            )
            Spacer(
                modifier = Modifier.height(20.dp),
            )
            Body5(
                text = state.content,
            )
        }
    }
}

@Preview
@Composable
fun NoticeDetailPreView() {

}