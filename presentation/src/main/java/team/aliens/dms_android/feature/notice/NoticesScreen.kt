package team.aliens.dms_android.feature.notice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.component.Notice
import team.aliens.design_system.component.NoticeList
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.rememberToast
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
import team.aliens.domain._model._common.Order
import team.aliens.domain._model.notice.FetchNoticesOutput
import team.aliens.domain.util.toDate
import team.aliens.presentation.R

fun FetchNoticesOutput.NoticeInformation.toNotice() = Notice(
    noticeId = this.id.toString(),
    title = this.title,
    createdAt = this.createdAt,
)

@Composable
internal fun NoticeScreen(
    navController: NavController,
    noticesViewModel: NoticesViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        noticesViewModel.fetchNoticeList()
    }

    val notices = remember {
        mutableStateListOf<Notice>()
    }

    val toast = rememberToast()

    // TODO 제거
    val badRequestComment = stringResource(id = R.string.BadRequest)
    val unAuthorizedComment = stringResource(id = R.string.UnAuthorized)
    val forbidden = stringResource(id = R.string.Forbidden)
    val tooManyRequestComment = stringResource(id = R.string.TooManyRequest)
    val serverException = stringResource(id = R.string.ServerException)
    val noInternetException = stringResource(id = R.string.NoInternetException)

    val isNoticeServiceEnabled = LocalAvailableFeatures.current[Extra.isNoticeServiceEnabled]

    // TODO 새 뷰모델 이벤트로 리팩토링 하기
    LaunchedEffect(Unit) {
        noticesViewModel.noticeViewEffect.collect {
            when (it) {
                is NoticesViewModel.Event.FetchNoticeList -> {
                    notices.clear()
                    val mappingNotice = it.fetchNoticesOutput.notices.map { item ->
                        item.toNotice()
                    }
                    for (i in 1..it.fetchNoticesOutput.notices.size) {
                        mappingNotice[i - 1].createdAt =
                            it.fetchNoticesOutput.notices[i - 1].createdAt.toDate()
                    }
                    notices.addAll(mappingNotice.toMutableStateList())
                }
                is NoticesViewModel.Event.BadRequestException -> {
                    toast(badRequestComment)
                }
                is NoticesViewModel.Event.UnAuthorizedTokenException -> {
                    toast(unAuthorizedComment)
                }
                is NoticesViewModel.Event.CannotConnectException -> {
                    toast(forbidden)
                }
                is NoticesViewModel.Event.TooManyRequestException -> {
                    toast(tooManyRequestComment)
                }
                is NoticesViewModel.Event.InternalServerException -> {
                    toast(serverException)
                }
                is NoticesViewModel.Event.UnknownException -> {
                    toast(noInternetException)
                }
                is NoticesViewModel.Event.FetchNoticeDetail -> {

                }
                is NoticesViewModel.Event.NullPointException -> {
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
            .background(
                DormTheme.colors.background,
            )
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Space(space = 24.dp)

        Body1(
            text = stringResource(R.string.Notice),
        )

        Space(space = 20.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            OrderButton(noticesViewModel)
        }

        NoticeList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            notices = notices,
            onClick = { noticeId ->
                navController.navigate("noticeDetail/${noticeId}")
            },
            errorMessage = stringResource(R.string.TheresNoNotices),
        )
    }
}

@Composable
private fun OrderButton(
    noticesViewModel: NoticesViewModel,
) {

    val context = LocalContext.current

    var text by remember {
        mutableStateOf(
            context.getString(R.string.LatestOrder),
        )
    }

    // TODO 새 뷰모델 state 로 리팩토링하기
    Button(
        onClick = {
            if (noticesViewModel.uiState.value.type == Order.NEW) {
                noticesViewModel.uiState.value.type = Order.OLD
                text = context.getString(R.string.OldestOrder)
                noticesViewModel.fetchNoticeList()
            } else {
                noticesViewModel.uiState .value.type = Order.NEW
                text = context.getString(R.string.LatestOrder)
                noticesViewModel.fetchNoticeList()
            }
        },
        border = BorderStroke(
            width = 1.dp,
            color = DormTheme.colors.onBackground,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DormTheme.colors.background,
        ),
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
            )
        }
    }
}

