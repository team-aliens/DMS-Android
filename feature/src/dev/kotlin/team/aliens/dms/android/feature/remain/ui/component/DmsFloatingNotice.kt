package team.aliens.dms.android.feature.remain.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelM

@Composable
fun DmsFloatingNotice(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconResource: Int = DmsIcon.Notification,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DmsTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp),
            ).padding(
                horizontal = 22.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
        )
        Text(
            text = text,
            style = DmsTheme.typography.labelM,
            color = DmsTheme.colorScheme.onBackground,
        )
    }
}
