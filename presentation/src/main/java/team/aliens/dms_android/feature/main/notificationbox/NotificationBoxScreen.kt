package team.aliens.dms_android.feature.main.notificationbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.layout.VerticallyFadedLazyColumn
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.typography.Caption
import team.aliens.dms_android.util.TopBar
import team.aliens.presentation.R

@Composable
internal fun NotificationBoxScreen(
    modifier: Modifier = Modifier,
    onPrevious: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(DormTheme.colors.background)
            .fillMaxSize(),
    ) {
        TopBar(
            title = stringResource(R.string.my_page_check_point_history),
            onPrevious = onPrevious,
        )
        /* Notifications(
             modifier = Modifier.fillMaxSize(),
             notifications = listOf(), // todo
         )*/
        Notification(
            icon = DormIcon.MyPage.drawableId,
            isNew = true,
        )
    }
}

// todo move to design system
@Composable
private fun Notifications(
    modifier: Modifier = Modifier,
    notifications: List<Any>, // todo
) {
    VerticallyFadedLazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

    }
}

@Composable
private fun Notification(
    modifier: Modifier = Modifier,
    icon: Int,
    isNew: Boolean,
) {
    Row(
        modifier = modifier
            .dormShadow(DormTheme.colors.primaryVariant)
            .fillMaxWidth()
            .background(
                color = if (isNew) {
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
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isNew) {
                DormTheme.colors.primary
            } else {
                DormTheme.colors.onBackground
            },
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Body3(
                text = "TEXTTEXT",
                color = Color.Black,
            )
            Caption(
                text = "CAPTIONCAPTION",
                color = Color.Black,
            )
        }
        Caption(
            text = "2년 전",
            color = Color.Black,
        )
    }
}
