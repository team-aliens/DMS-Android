package team.aliens.dms.android.core.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import team.aliens.dms.android.designsystem.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    darkIcon: Boolean = isSystemInDarkTheme(),
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(
                id = if (darkIcon) {
                    R.drawable.ic_logo_dark
                } else {
                    R.drawable.ic_logo
                },
            ),
            contentDescription = null,
        )
    }
}
