package team.aliens.dms.android.feature.notification.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsIcon
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.Switch
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.data.notification.model.NotificationTopicGroup
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.notification.navigation.NotificationSettingsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun NotificationSettingsScreen(
    modifier: Modifier = Modifier,
    navigator: NotificationSettingsNavigator,
) {
    val viewModel: NotificationSettingsViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val toast = LocalToast.current

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when(sideEffect) {
            NotificationSettingsSideEffect.CurrentNotificationsStatusNotFound -> toast.showErrorToast(
                message = context.getString(R.string.notification_not_current)
            )
            NotificationSettingsSideEffect.SubscribeNotificationSuccess -> toast.showSuccessToast(
                message = context.getString(R.string.notification_subscribe_success)
            )
            NotificationSettingsSideEffect.SubscribeNotificationFailure -> toast.showErrorToast(
                message = context.getString(R.string.notification_subscribe_fail)
            )
            NotificationSettingsSideEffect.UnSubscribeNotificationSuccess -> toast.showSuccessToast(
                message = context.getString(R.string.notification_unsubscribe_success)
            )
            NotificationSettingsSideEffect.UnSubscribeNotificationFailure -> toast.showErrorToast(
                message = context.getString(R.string.notification_unsubscribe_fail)
            )
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = { Text(text = stringResource(id = R.string.notification_settings)) },
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
                .padding(paddingValues)
                .topPadding(PaddingDefaults.ExtraLarge),
            verticalArrangement = Arrangement.spacedBy(34.dp),
        ) {
            Notifications(
                status = uiState.status,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun Notifications(
    status: List<NotificationTopicGroup.Status>,
    viewModel: NotificationSettingsViewModel,
) {
    val noticeNotifications = remember {
        listOf(
            Notification(
                titleRes = R.string.notification_channel_notice_title,
                descriptionRes = R.string.notification_channel_notice_description,
            ),
        )
    }
    val studyRoomNotifications = remember {
        listOf(
            Notification(
                titleRes = R.string.notification_channel_use_study_room_title,
                descriptionRes = R.string.notification_channel_use_study_room_description,
            ),
            Notification(
                titleRes = R.string.notification_channel_application_time_study_room_title,
                descriptionRes = R.string.notification_channel_application_time_study_room_description,
            ),
        )
    }
    val pointNotifications = remember {
        listOf(
            Notification(
                titleRes = R.string.notification_channel_point_title,
                descriptionRes = R.string.notification_channel_point_description,
            ),
        )
    }
    val outingNotifications = remember {
        listOf(
            Notification(
                titleRes = R.string.notification_channel_application_outing_title,
                descriptionRes = R.string.notification_channel_application_outing_description,
            ),
        )
    }
    status.forEach {
        val notifications = when (it.topicGroup) {
            NotificationTopicGroup.NOTICE -> noticeNotifications
            NotificationTopicGroup.STUDY_ROOM -> studyRoomNotifications
            NotificationTopicGroup.POINT -> pointNotifications
            NotificationTopicGroup.OUTING -> outingNotifications
        }
        NotificationLayout(
            viewModel = viewModel,
            title = it.groupName,
            notifications = notifications,
            topicSubscription = it.topicSubscriptions,
        )
    }
}

@Composable
private fun NotificationLayout(
    modifier: Modifier = Modifier,
    viewModel: NotificationSettingsViewModel,
    title: String,
    notifications: List<Notification>,
    topicSubscription: List<NotificationTopicGroup.Status.TopicSubscription>,
) {
    Column(
        modifier = modifier
            .horizontalPadding(),
        verticalArrangement = Arrangement.spacedBy(PaddingDefaults.Large)
    ) {
        Text(
            text = title,
            style = DmsTheme.typography.title3,
            color = DmsTheme.colorScheme.surfaceVariant,
        )
        notifications.forEachIndexed { index, notification ->
            val subscribed = topicSubscription[index].subscribed
            val topic = topicSubscription[index].topic
            var isSwitchEnabled by remember { mutableStateOf(subscribed) }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = stringResource(id = notification.titleRes),
                        style = DmsTheme.typography.body2,
                        color = DmsTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = stringResource(id = notification.descriptionRes),
                        style = DmsTheme.typography.caption,
                        color = DmsTheme.colorScheme.surfaceVariant,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isSwitchEnabled,
                    onCheckedChange = { isChecked ->
                        isSwitchEnabled = isChecked
                        viewModel.postIntent(
                            NotificationSettingsIntent.UpdateNotificationTopic(
                                isSubscribed = isChecked,
                                topic = topic,
                            )
                        )
                    },
                )
            }
        }
    }
}

private class Notification(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
)
