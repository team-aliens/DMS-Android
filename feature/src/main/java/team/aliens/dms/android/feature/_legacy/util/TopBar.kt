package team.aliens.dms.android.feature._legacy.util
/*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.designsystem.icon.DormIcon
import team.aliens.dms.android.designsystem.modifier.dormClickable
import team.aliens.dms.android.designsystem.modifier.dormShadow
import team.aliens.dms.android.designsystem.DmsTheme
import team.aliens.dms.android.designsystem.typography.Body1
import team.aliens.dms.android.feature.R

@Composable
fun TopBar(
    title: String,
    onPrevious: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .dormShadow(DmsTheme.colors.primaryVariant)
            .fillMaxWidth()
            .background(DmsTheme.colors.surface)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .dormClickable(
                    rippleEnabled = false,
                ) { if (onPrevious != null) onPrevious() },
            painter = painterResource(DormIcon.BackArrow.drawableId),
            contentDescription = stringResource(R.string.top_bar_back_button),
            tint = DmsTheme.colors.onSurface,
        )
        Spacer(Modifier.width(16.dp))
        Body1(
            modifier = Modifier.weight(1f),
            text = title,
        )
    }
}
*/
