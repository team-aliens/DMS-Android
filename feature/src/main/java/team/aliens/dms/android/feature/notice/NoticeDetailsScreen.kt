package team.aliens.dms.android.feature.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.notice.navigation.NoticeNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun NoticeDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: NoticeNavigator,
    noticeId: UUID,
) {
    val viewModel: NoticeDetailsViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(noticeId) {
        viewModel.postIntent(NoticeDetailsIntent.FetchNoticeDetails(noticeId))
    }

    DmsScaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.announcement)) },
                navigationIcon = {
                    IconButton(onClick = navigator::popBackStack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier.padding(padValues),
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            if (uiState.title != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding()
                        .topPadding(),
                    text = uiState.title!!,
                    style = DmsTheme.typography.title2,
                    color = DmsTheme.colorScheme.onSurface,
                )
            } else {
                // TODO handle null
            }
            if (uiState.createdAt != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(),
                    text = uiState.createdAt.toString(),
                    style = DmsTheme.typography.caption,
                    color = DmsTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                // TODO handle null
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalPadding(),
                color = DmsTheme.colorScheme.line,
            )
            if (uiState.content != null) {
                Text(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .horizontalPadding(),
                    text = uiState.content!!,
                    style = DmsTheme.typography.body3,
                    color = DmsTheme.colorScheme.onSurface,
                )
            } else {
                // TODO handle null
            }
        }
    }
}
