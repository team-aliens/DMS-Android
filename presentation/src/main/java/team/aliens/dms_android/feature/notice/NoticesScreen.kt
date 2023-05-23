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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
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
import team.aliens.domain._model._common.Order
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

    LaunchedEffect(Unit) {
        noticesViewModel.onEvent(
            event = NoticesUiEvent.FetchNotices(
                order = Order.NEW,
            ),
        )
    }

    val appState = rememberDmsAppState()

    val notices = remember {
        mutableStateListOf<Notice>()
    }

    val isNoticeServiceEnabled = LocalAvailableFeatures.current[Extra.isNoticeServiceEnabled]

    LaunchedEffect(Unit) {
        noticesViewModel.onEvent(
            event = NoticesUiEvent.FetchNotices(
                order = noticesViewModel.uiState.value.order,
            )
        )
    }

    LaunchedEffect(Unit) {
        noticesViewModel.uiState.collectLatest { it ->
            notices.clear()
            notices.addAll(it.notices.map { it.toNotice() })

            // TODO DmsAppState 에서 showToast 함수 구현하기
            appState.scaffoldState.snackbarHostState.showSnackbar(
                message = it.noticeErrorMessage,
                actionLabel = ToastType.ERROR.toString(),
            )
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
                navController.navigate("noticeDetails/${noticeId}")
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
            when (noticesViewModel.uiState.value.order) {
                Order.NEW -> {
                    noticesViewModel.onEvent(
                        event = NoticesUiEvent.FetchNotices(
                            order = Order.OLD,
                        )
                    )
                    text = context.getString(R.string.oldest_order)
                }

                Order.OLD -> {
                    noticesViewModel.onEvent(
                        event = NoticesUiEvent.FetchNotices(
                            order = Order.NEW,
                        )
                    )
                    text = context.getString(R.string.latest_order)
                }
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

