package team.aliens.dms.android.feature.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.design_system.layout.VerticallyFadedLazyColumn
import team.aliens.dms.android.design_system.modifier.dormClickable
import team.aliens.dms.android.design_system.modifier.dormShadow
import team.aliens.dms.android.design_system.theme.DormTheme
import team.aliens.dms.android.design_system.typography.Body3
import team.aliens.dms.android.design_system.typography.Caption
import team.aliens.dms.android.domain.model.notification.Notification
import team.aliens.dms.android.domain.model.notification.NotificationTopic
import team.aliens.dms_android.feature.R
import team.aliens.dms.android.feature._legacy.util.Now
import team.aliens.dms.android.feature._legacy.util.TopBar
import team.aliens.dms.android.feature._legacy.util.toDate
import team.aliens.dms.android.feature.notification.navigation.NotificationNavigation

@Destination
@Composable
internal fun NotificationBoxScreen(
    modifier: Modifier = Modifier,
    navigator: NotificationNavigation,
    notificationBoxViewModel: NotificationBoxViewModel = hiltViewModel(),
) {
    val uiState by notificationBoxViewModel.stateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.my_page_check_point_history),
            onPrevious = navigator::popBackStack,
        )
        Notifications(
            modifier = Modifier.fillMaxWidth(),
            newNotifications = uiState.newNotifications,
            priorNotifications = uiState.priorNotifications,
        )
    }
}

// todo move to design system
@Composable
private fun Notifications(
    modifier: Modifier = Modifier,
    newNotifications: List<Notification>,
    priorNotifications: List<Notification>,
) {
    println(newNotifications)
    println(priorNotifications)
    VerticallyFadedLazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (newNotifications.isNotEmpty()) {
            items(newNotifications.also { println("NEWNEW $it") }) { newNotification ->
                Notification(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    notification = newNotification,
                )
            }
        }
        if (priorNotifications.isNotEmpty()) {
            items(priorNotifications.also { println("PRIPRI $it") }) { priorNotification ->
                Notification(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    notification = priorNotification,
                )
            }
        }
    }
}

@Composable
private fun Notification(
    modifier: Modifier = Modifier,
    notification: Notification,
) {
    Row(
        modifier = modifier
            .dormShadow(DormTheme.colors.primaryVariant)
            .fillMaxWidth()
            .background(
                color = if (!notification.read) {
                    DormTheme.colors.surface
                } else {
                    DormTheme.colors.surface
                },
                shape = RoundedCornerShape(10.dp),
            )
            .clip(RoundedCornerShape(10.dp))
            .dormClickable {
                // todo
            }
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            painter = painterResource(
                when (notification.topic) {
                    NotificationTopic.NOTICE -> R.drawable.ic_notice
                },
            ),
            contentDescription = null,
            tint = if (!notification.read) {
                DormTheme.colors.primary
            } else {
                DormTheme.colors.onBackground
            },
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Body3(
                text = notification.title,
            )
            Caption(
                text = notification.content,
            )
        }
        Caption(
            text = createdAtDateToFormattedString(notification.createdAt),
        )
    }
}

@Composable
private fun createdAtDateToFormattedString(createdAt: String): String {
    val formattedDate = createdAt.toDate()
    val now = Now()

    when (formattedDate.time - now.time) {

    }
    return "8일 전"
}
