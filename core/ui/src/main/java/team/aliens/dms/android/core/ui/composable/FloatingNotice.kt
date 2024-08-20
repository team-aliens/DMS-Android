package team.aliens.dms.android.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.verticalPadding

@Composable
fun FloatingNotice(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = modifier
            .verticalPadding()
            .fillMaxWidth()
            .shadow(shape = DmsTheme.shapes.circle)
            .clip(DmsTheme.shapes.circle)
            .background(DmsTheme.colorScheme.surface)
            .verticalPadding()
            .horizontalPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = team.aliens.dms.android.core.designsystem.R.drawable.ic_notice),
            tint = DmsTheme.colorScheme.primary,
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .verticalPadding(PaddingDefaults.ExtraSmall),
            text = text,
            style = DmsTheme.typography.body3,
            color = DmsTheme.colorScheme.onSurface,
        )
    }
}
