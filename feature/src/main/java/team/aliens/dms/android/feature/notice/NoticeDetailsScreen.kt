package team.aliens.dms.android.feature.notice

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.notice.navigation.NoticeNavigator
import java.util.UUID

@Destination
@Composable
internal fun NoticeDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: NoticeNavigator,
    noticeId: UUID,
) {/*
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
