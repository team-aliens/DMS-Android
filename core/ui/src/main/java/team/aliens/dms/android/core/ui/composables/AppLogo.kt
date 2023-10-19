package team.aliens.dms.android.core.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    darkIcon: Boolean = isSystemInDarkTheme(),
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier.size(
                width = 124.dp,
                height = 44.dp,
            ),
            painter = painterResource(
                id = if (darkIcon) {
                    R.drawable.ic_logo_dark
                } else {
                    R.drawable.ic_logo_light
                },
            ),
            contentDescription = null,
        )
    }
}
