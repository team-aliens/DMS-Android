package team.aliens.dms.android.core.designsystem.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
fun DmsIconButton(
    @DrawableRes resource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = DmsTheme.colorScheme.onBackground,
    enabled: Boolean = true,
    size: Dp = 26.dp,
    contentPaddingValues: PaddingValues = PaddingValues(2.dp),
    contentDescription: String? = null,
) {
    IconButton(
        modifier = modifier.size(size),
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.padding(contentPaddingValues),
            painter = painterResource(resource),
            tint = tint,
            contentDescription = contentDescription,
        )
    }
}
