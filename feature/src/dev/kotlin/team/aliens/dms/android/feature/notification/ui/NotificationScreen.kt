package team.aliens.dms.android.feature.notification.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.startPadding
import team.aliens.dms.android.core.designsystem.tab.DmsTab
import team.aliens.dms.android.core.designsystem.tab.DmsTabRow
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.notification.ui.component.NoticeItem
import team.aliens.dms.android.feature.notification.viewmodel.NotificationSideEffect
import team.aliens.dms.android.feature.notification.viewmodel.NotificationViewModel
import team.aliens.dms.android.feature.notification.viewmodel.NotificationState
import team.aliens.dms.android.feature.notification.viewmodel.NotificationUi
import java.util.UUID

@Composable
internal fun Notification(
    onBackClick: () -> Unit,
    onNavigateNotificationDetailClick: (UUID) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: NotificationViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val tabData = listOf(
        "알림",
        "공지",
    )
    val pagerState = rememberPagerState(
        pageCount = { tabData.size },
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is NotificationSideEffect.FailFetchNotification -> onShowSnackBar(
                    DmsSnackBarType.ERROR, "알림 조회를 실패했어요"
                )
                is NotificationSideEffect.FailUpdateNotification -> onShowSnackBar(
                    DmsSnackBarType.ERROR, "업데이트 실패 했어요"
                )
            }
        }
    }

    NotificationScreen(
        state = state,
        tabData = tabData.toPersistentList(),
        pagerState = pagerState,
        tabIndex = tabIndex,
        onTabClick = { page ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(page)
            }
        },
        onBackClick = onBackClick,
        onNotificationDetailClick = { linkId, notificationId ->
            viewModel.updateNotificationReadStatus(notificationId)
            onNavigateNotificationDetailClick(linkId)
        },
        onNotificationClick = { point, notificationId ->
            viewModel.updateNotificationReadStatus(notificationId)
            onNavigatePointHistory(point)
        },
    )
}

@Composable
private fun NotificationScreen(
    state: NotificationState,
    tabData: ImmutableList<String>,
    pagerState: PagerState,
    tabIndex: Int,
    onTabClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onNotificationDetailClick: (UUID, UUID) -> Unit,
    onNotificationClick: (PointType, UUID) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.background)
            .systemBarsPadding(),
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
        ) { page ->
            if (page == 0) {
                NotificationItems(
                    notifications = state.notifications.toPersistentList(),
                    onNotificationClick = onNotificationClick,
                )
            } else {
                NoticeItems(
                    notices = state.notices,
                    onNotificationDetailClick = onNotificationDetailClick,
                )
            }
        }

    }
}

@Composable
internal fun NotificationItems(
    modifier: Modifier = Modifier,
    notifications: ImmutableList<NotificationUi>,
    onNotificationClick: (PointType, UUID) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(
            items = notifications,
            key = { item -> item.id }
        ) {notification ->
            NotificationItem(
                notification = notification,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

@Composable
private fun NoticeItems(
    modifier: Modifier = Modifier,
    notices: List<NotificationUi>,
    onNotificationDetailClick: (UUID, UUID) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            items = notices,
            key = { item -> item.id },
        ) { notice ->
            NoticeItem(
                notice = notice,
                onNotificationDetailClick = onNotificationDetailClick,
            )
        }
    }
}

@Composable
internal fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: NotificationUi,
    onNotificationClick: (PointType, UUID) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onNotificationClick(notification.pointDetailTopic, notification.id) })
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(if (notification.pointDetailTopic == PointType.MINUS) DmsIcon.Minus else DmsIcon.Plus),
            contentDescription = null,
        )
        Column(
            modifier = modifier.startPadding(12.dp),
        ) {
            Text(
                text = notification.title,
                style = DmsTheme.typography.bodyM,
            )
            Row(
                modifier = modifier.topPadding(6.dp)
            ) {
                if (!notification.isRead) {
                    Icon(
                        modifier = modifier.size(4.dp),
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        tint = DmsTheme.colorScheme.primaryContainer,
                    )
                }
                Text(
                    modifier = modifier
                        .startPadding(4.dp),
                    text = notification.content,
                    style = DmsTheme.typography.labelM,
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = notification.elapsedText,
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
