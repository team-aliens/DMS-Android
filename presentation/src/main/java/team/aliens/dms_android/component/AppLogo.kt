package team.aliens.dms_android.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.presentation.R

@Composable
fun AppLogo(
    darkIcon: Boolean = isSystemInDarkTheme(),
) {
    Box(
        modifier = Modifier.size(
            width = 96.dp,
            height = 34.dp,
        ),
    ) {
        Image(
            painter = painterResource(
                id = if (darkIcon) R.drawable.ic_logo_dark else R.drawable.ic_logo,
            ),
            contentDescription = null,
        )
    }
}