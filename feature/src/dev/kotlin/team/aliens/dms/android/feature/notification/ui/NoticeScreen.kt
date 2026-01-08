package team.aliens.dms.android.feature.notification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.tab.DmsTab
import team.aliens.dms.android.core.designsystem.tab.DmsTabRow
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.feature.notification.viewmodel.NoticeUi
import team.aliens.dms.android.feature.notification.viewmodel.NoticeViewModel
import team.aliens.dms.android.feature.notification.viewmodel.NotificationState
import java.util.UUID

@Composable
internal fun Notices(
    onNavigateBack: () -> Unit,
    onNoticeDetailClick: (UUID) -> Unit,
) {
    val viewModel: NoticeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val tabData = listOf(
        "알림",
        "공지",
    )
    val pagerState = rememberPagerState(
        pageCount = { tabData.size },
        initialPage = 1,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    NoticesScreen(
        state = state,
        tabData = tabData.toPersistentList(),
        pagerState = pagerState,
        tabIndex = tabIndex,
        onTabClick = {page ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(page)
            }
        },
        onBackClick = onNavigateBack,
        onNoticeDetailClick = onNoticeDetailClick,
    )
}

@Composable
private fun NoticesScreen(
    state: NotificationState,
    tabData: ImmutableList<String>,
    pagerState: PagerState,
    tabIndex: Int,
    onTabClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onNoticeDetailClick: (UUID) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackClick,
        )
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabData.forEachIndexed { index, text ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = { onTabClick(index) },
                    text = text,
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            beyondViewportPageCount = 1
        ) {
            if (pagerState.currentPage == 0) {
                NotificationItems(
                    notifications = state.notifications.toPersistentList(),
                )
            } else {
                NoticeItems(
                    notices = state.notices,
                    onNoticeDetailClick = onNoticeDetailClick,
                )
            }
        }

    }
}

@Composable
internal fun NotificationItems(
    modifier: Modifier = Modifier,
    notifications: ImmutableList<NoticeUi>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(
            items = notifications,
            key = { item -> item.id }
        ) {
            Row(
                modifier = modifier.padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Icon(
                    painter = painterResource(DmsIcon.Notification),
                    contentDescription = null,
                )
                Column {
                    Text(
                        text = it.title
                    )
                    Text(
                        text = it.content ?: ""
                    )
                }
                Text(
                    text = it.elapsedText
                )
            }
        }
    }
}

@Composable
private fun NoticeItems(
    modifier: Modifier = Modifier,
    notices: List<NoticeUi>,
    onNoticeDetailClick: (UUID) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(
            items = notices,
            key = { item -> item.id },
        ) { notice ->
            NoticeItem(
                notice = notice,
                onNoticeDetailClick = onNoticeDetailClick,
            )
        }
    }
}

@Composable
internal fun NotificationItem() {

}


@Composable
internal fun NoticeItem(
    modifier: Modifier = Modifier,
    notice: NoticeUi,
    onNoticeDetailClick: (UUID) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onNoticeDetailClick(notice.id) })
            .padding(horizontal = 24.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(DmsIcon.Notice),
            tint = DmsTheme.colorScheme.scrim,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            text = notice.title,
            maxLines = 1,
            style = DmsTheme.typography.bodyM,
            color = DmsTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = notice.elapsedText,
            style = DmsTheme.typography.bodyM,
            color = DmsTheme.colorScheme.inverseSurface,
        )
        Icon(
            painter = painterResource(DmsIcon.Forward),
            tint = DmsTheme.colorScheme.scrim,
            contentDescription = null,
        )
    }
}
