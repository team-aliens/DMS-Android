package team.aliens.dms.android.feature.main.home.model

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.button.DmsIconButton
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.util.clickable

@Composable
internal fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    onOutingPassClick: () -> Unit,
    onNotificationClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.clickable(onClick = onOutingPassClick),
            painter = painterResource(DmsIcon.OutingPass),
            tint = DmsTheme.colorScheme.scrim,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsIconButton(
            resource = DmsIcon.Notification,
            tint = DmsTheme.colorScheme.scrim,
            contentPaddingValues = PaddingValues(2.dp),
            onClick = onNotificationClick,
        )
    }
}
