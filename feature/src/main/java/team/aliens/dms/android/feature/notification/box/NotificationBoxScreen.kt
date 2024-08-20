package team.aliens.dms.android.feature.notification.box

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.data.notification.model.Notification
import team.aliens.dms.android.data.notification.model.NotificationTopic
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.notification.navigation.NotificationBoxNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun NotificationBoxScreen(
    modifier: Modifier = Modifier,
    navigator: NotificationBoxNavigator,
) {
    val viewModel: NotificationBoxViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val toast = LocalToast.current
    val context = LocalContext.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            NotificationBoxSideEffect.CurrentNotificationsNotFound -> toast.showErrorToast(
                message = context.getString(R.string.notification_box_notifications_not_current),
            )

            is NotificationBoxSideEffect.MoveToDetail -> {
                navigator.openNoticeDetails(noticeId = sideEffect.detailId)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.notification_box)) },
                navigationIcon = {
                    IconButton(navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = DmsIcon.Back),
                            contentDescription = stringResource(id = R.string.top_bar_back_button),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DmsTheme.colorScheme.surface)
                .padding(paddingValues),
        ) {
            if (uiState.notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.notification_box_not_exist),
                        style = DmsTheme.typography.body2,
                        color = DmsTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                NotificationList(
                    viewModel = viewModel,
                    notifications = uiState.notifications,
                )
            }
        }
    }
}

@Composable
private fun NotificationList(
    modifier: Modifier = Modifier,
    viewModel: NotificationBoxViewModel,
    notifications: List<Notification>,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .topPadding(),
        verticalArrangement = Arrangement.spacedBy(PaddingDefaults.Medium),
    ) {
        NotificationListLayout(
            viewModel = viewModel,
            isRead = false,
            notifications = notifications.filter { !it.isRead },
        )
        NotificationListLayout(
            viewModel = viewModel,
            isRead = true,
            notifications = notifications.filter { it.isRead },
        )
    }
}

@Composable
private fun NotificationListLayout(
    modifier: Modifier = Modifier,
    viewModel: NotificationBoxViewModel,
    isRead: Boolean,
    notifications: List<Notification>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(),
        verticalArrangement = Arrangement.spacedBy(PaddingDefaults.Medium),
    ) {
        if (notifications.isNotEmpty()) {
            Text(
                text = stringResource(id = if (isRead) R.string.notification_box_read else R.string.notification_box_not_read),
                style = DmsTheme.typography.caption,
                color = DmsTheme.colorScheme.onSurfaceVariant,
            )
            notifications.forEach { notification ->
                NotificationCard(
                    modifier = Modifier.shadow(),
                    isRead = isRead,
                    notification = notification,
                    onNavigateToNoticeDetails = {
                        viewModel.postIntent(
                            NotificationBoxIntent.DetailNotification(
                                notification,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun NotificationCard(
    modifier: Modifier = Modifier,
    isRead: Boolean,
    notification: Notification,
    onNavigateToNoticeDetails: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(DmsTheme.colorScheme.surface)
            .clickable(
                enabled = notification.topic == NotificationTopic.NOTICE,
                onClick = onNavigateToNoticeDetails,
            )
            .padding(
                horizontal = PaddingDefaults.Medium,
                vertical = PaddingDefaults.Small,
            ),
    ) {
        Icon(
            painter = painterResource(id = DmsIcon.BlueNotice),
            contentDescription = stringResource(id = R.string.notice),
            tint = if (isRead) {
                DmsTheme.colorScheme.onBackground
            } else {
                DmsTheme.colorScheme.primary
            },
        )
        Spacer(modifier = Modifier.width(PaddingDefaults.Small))
        Column {
            Text(
                text = notification.title,
                style = DmsTheme.typography.body3,
            )
            Text(
                text = notification.content,
                style = DmsTheme.typography.caption,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = notification.createdAt.text,
            style = DmsTheme.typography.caption,
        )
    }
}

private val LocalDateTime.text: String
    @Composable inline get() = stringResource(
        id = R.string.notification_box_format_time,
        this.monthValue,
        this.dayOfMonth,
    )
