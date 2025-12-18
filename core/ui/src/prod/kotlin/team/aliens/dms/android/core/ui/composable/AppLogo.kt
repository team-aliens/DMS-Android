package team.aliens.dms.android.core.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import team.aliens.dms.android.core.designsystem.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    darkIcon: Boolean = isSystemInDarkTheme(),
) {
    Image(
        modifier = modifier,
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
