package team.aliens.dms_android.feature.notice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title3
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
            .background(DormColor.Gray200),
    ) {

        TopBar(
            title = stringResource(
                id = R.string.Announcement,
            ),
        ) {
            navController.popBackStack()
        }


        Column(
            modifier = Modifier
                .background(DormColor.Gray200)
                .padding(
                    top = 40.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                // title
                Title3(
                    text = state.title,
                )

                // date
                Caption(
                    text = state.createAt,
                    color = DormColor.Gray500,
                )

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = DormColor.Gray300,
                )
            }

            // Content
            Body5(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState(),
                    )
                    .padding(
                        top = 8.dp,
                    ),
                text = state.content,
            )
        }
    }
}
