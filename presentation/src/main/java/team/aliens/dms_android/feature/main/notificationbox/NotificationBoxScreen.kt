package team.aliens.dms_android.feature.main.notificationbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.aliens.design_system.layout.VerticallyFadedLazyColumn
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.util.TopBar
import team.aliens.domain.model.notification.Notification
import team.aliens.domain.model.notification.NotificationTopic
import team.aliens.presentation.R

@Composable
internal fun NotificationBoxScreen(
    modifier: Modifier = Modifier,
    notificationBoxViewModel: NotificationBoxViewModel = hiltViewModel(),
    onPrevious: () -> Unit,
) {
    val uiState by notificationBoxViewModel.stateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.my_page_check_point_history),
            onPrevious = onPrevious,
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
            items(newNotifications) { newNotification ->
                Notification(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    notification = newNotification,
                )
            }
        }
        if (priorNotifications.isNotEmpty()) {
            items(priorNotifications) { priorNotification ->
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
                color = if (notification.read) {
                    DormTheme.colors.secondary
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
            tint = if (notification.read) {
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
                color = Color.Black,
            )
            Caption(
                text = notification.content,
                color = Color.Black,
            )
        }
        Caption(
            text = notification.createdAt,
            color = Color.Black,
        )
    }
}
