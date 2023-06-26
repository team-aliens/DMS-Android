package team.aliens.dms_android.feature.main.home.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.UUID
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body5
import team.aliens.design_system.typography.Caption
import team.aliens.design_system.typography.Title3
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Composable
internal fun NoticeDetailsScreen(
    onPrevious: () -> Unit,
    noticeId: UUID,
    noticeDetailsViewModel: NoticeDetailsViewModel = hiltViewModel(),
) {
    val uiState by noticeDetailsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        noticeDetailsViewModel.onEvent(NoticeDetailsUiEvent.FetchNoticeDetails(noticeId))
    }

    Column(
        modifier = Modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.Announcement),
            onPrevious = onPrevious,
        )
        Column(
            modifier = Modifier
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
                Title3(text = uiState.title)
                Caption(
                    text = uiState.createdAt.toNoticeDate(),
                    color = DormTheme.colors.primaryVariant,
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = DormTheme.colors.secondaryVariant,
                )
            }
            Body5(
                modifier = Modifier
                    .verticalScroll(
                        rememberScrollState(),
                    )
                    .padding(
                        top = 8.dp,
                    ),
                text = uiState.content,
            )
        }
    }
}
