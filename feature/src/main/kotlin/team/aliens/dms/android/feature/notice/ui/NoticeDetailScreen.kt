package team.aliens.dms.android.feature.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.horizontalPadding
import team.aliens.dms.android.core.designsystem.lBodyB
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.designsystem.verticalPadding
import team.aliens.dms.android.core.ui.navigation.LocalResultStore
import team.aliens.dms.android.feature.notice.viewmodel.NoticeDetailSideEffect
import team.aliens.dms.android.feature.notice.viewmodel.NoticeDetailState
import team.aliens.dms.android.feature.notice.viewmodel.NoticeDetailUi
import team.aliens.dms.android.feature.notice.viewmodel.NoticeDetailViewModel
import java.util.UUID

@Composable
internal fun NoticeDetail(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: NoticeDetailViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val resultStore = LocalResultStore.current
    val updatedOnShowSnackBar by rememberUpdatedState(onShowSnackBar)

    LaunchedEffect(Unit) {
        snapshotFlow {
            resultStore.resultStateMap["notice_detail_result"]?.value as? UUID?
        }.collect { result ->
            if (result != null) {
                viewModel.getNotificationDetail(result)
                resultStore.removeResult<String?>(resultKey = "notice_detail_result")
            } else {
                updatedOnShowSnackBar(DmsSnackBarType.ERROR, "정보를 가져오지 못 했어요")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is NoticeDetailSideEffect.FailNoticeDetail -> updatedOnShowSnackBar(
                    DmsSnackBarType.ERROR, "데이터를 조회할 수 없어요"
                )
            }
        }
    }

    NoticeDetailsScreen(
        state = state,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun NoticeDetailsScreen(
    state: NoticeDetailState,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        DmsTopAppBar(title = "안내", onBackClick = onNavigateBack, )
        NotificationDetailContent(
            notice = state.notice,
        )
    }
}

@Composable
private fun NotificationDetailContent(
    notice: NoticeDetailUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(10.dp)
            .background(color = DmsTheme.colorScheme.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(vertical = 24.dp, horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = notice.title,
            style = DmsTheme.typography.lBodyB,
            color = DmsTheme.colorScheme.tertiaryContainer,
        )
        Text(
            modifier = Modifier.topPadding(8.dp),
            text = notice.elapsedCreatedAt,
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .verticalPadding(18.dp),
            thickness = 1.dp,
            color = DmsTheme.colorScheme.scrim,
        )
        Text(
            text = notice.content ?: "",
            style = DmsTheme.typography.bodyM,
            color = DmsTheme.colorScheme.tertiaryContainer,
        )
    }
}
