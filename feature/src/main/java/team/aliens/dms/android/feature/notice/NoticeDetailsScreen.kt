package team.aliens.dms.android.feature.notice

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
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
        }
    ) { padValues ->

    }
    /*
    val uiState by noticeDetailsViewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        // noticeDetailsViewModel.postIntent(NoticeDetailsIntent.FetchNoticeDetails(noticeId))
    }

    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.Announcement),
            onPrevious = navigator::popBackStack,
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
    }*/
}
