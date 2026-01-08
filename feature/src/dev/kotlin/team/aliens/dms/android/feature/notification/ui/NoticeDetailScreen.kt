package team.aliens.dms.android.feature.notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import team.aliens.dms.android.feature.notification.viewmodel.NoticeDetailState
import team.aliens.dms.android.feature.notification.viewmodel.NoticeDetailUi
import team.aliens.dms.android.feature.notification.viewmodel.NoticeDetailViewModel
import java.util.UUID

@Composable
internal fun NoticeDetail(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: NoticeDetailViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val resultStore = LocalResultStore.current

    LaunchedEffect(Unit) {
        snapshotFlow {
            resultStore.resultStateMap["notice_result"]?.value as? UUID?
        }.collect { result ->
            if (result != null) {
                viewModel.getNoticeDetail(result)
                resultStore.removeResult<String?>(resultKey = "vote_result")
            } else {
                onShowSnackBar(DmsSnackBarType.ERROR, "정보를 가져오지 못 했어요")
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
    ) {
        DmsTopAppBar(
            title = "안내",
            onBackPressed = onNavigateBack,
        )
        Notice(
            notice = state.notice,
        )
    }
}

@Composable
private fun Notice(
    modifier: Modifier = Modifier,
    notice: NoticeDetailUi,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(10.dp)
            .background(color = DmsTheme.colorScheme.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(vertical = 24.dp, horizontal = 32.dp)
            .horizontalScroll(rememberScrollState()),
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
