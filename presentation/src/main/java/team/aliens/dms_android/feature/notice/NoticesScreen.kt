package team.aliens.dms_android.feature.notice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.design_system.component.Notice
import team.aliens.design_system.component.NoticeList
import team.aliens.design_system.extension.Space
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.ToastType
import team.aliens.design_system.typography.Body1
import team.aliens.design_system.typography.ButtonText
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.application.rememberDmsAppState
import team.aliens.domain._model.notice.FetchNoticesOutput
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
    // TODO 인자로 전달받기
    val appState = rememberDmsAppState()

    val state = noticesViewModel.uiState.collectAsState()

    val notices = state.value.notices

    val isNoticeServiceEnabled = LocalAvailableFeatures.current[Extra.isNoticeServiceEnabled]

    LaunchedEffect(Unit) {
        // TODO DmsAppState toastManager setMessage 함수 사용하기
        appState.scaffoldState.snackbarHostState.showSnackbar(
            message = state.value.noticeErrorMessage,
            actionLabel = ToastType.ERROR.toString(),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DormTheme.colors.background,
            )
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Body1(
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally,
            ),
            text = stringResource(R.string.Notice),
        )
        Space(space = 20.dp)
        OrderButton(
            noticesViewModel = noticesViewModel,
            orderText = state.value.orderText,
        )
        Space(space = 8.dp)
        NoticeList(
            notices = notices.map { it.toNotice() },
            onClick = { noticeId ->
                navController.navigate("noticeDetails/${noticeId}")
            },
            errorMessage = stringResource(R.string.TheresNoNotices),
        )
    }
}

@Composable
private fun OrderButton(
    noticesViewModel: NoticesViewModel,
    orderText: String,
) {
    Button(
        onClick = {
            noticesViewModel.onEvent(
                event = NoticesUiEvent.SetNoticeOrder,
            )
        },
        border = BorderStroke(
            width = 1.dp,
            color = DormTheme.colors.onBackground,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DormTheme.colors.background,
        ),
    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            ButtonText(
                text = orderText,
            )
        }
    }
}

