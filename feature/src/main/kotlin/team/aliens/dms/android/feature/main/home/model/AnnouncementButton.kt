package team.aliens.dms.android.feature.main.home.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelB
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.util.clickable

@Composable
internal fun AnnouncementButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(DmsTheme.colorScheme.surfaceTint)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(DmsIcon.Announcement),
            contentDescription = null,
            tint = DmsTheme.colorScheme.scrim,
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "공지 보러가기",
            style = DmsTheme.typography.labelB,
            color = DmsTheme.colorScheme.tertiaryContainer,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = title,
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.inverseSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(DmsIcon.Forward),
            contentDescription = null,
            tint = DmsTheme.colorScheme.scrim,
        )
    }
}
