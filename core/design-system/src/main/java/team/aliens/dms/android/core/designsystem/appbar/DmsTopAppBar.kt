package team.aliens.dms.android.core.designsystem.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.button.DmsIconButton
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon

@Composable
fun DmsTopAppBar(
    modifier: Modifier = Modifier,
    showLogo: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    title: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = if (showLogo || onBackClick != null) Arrangement.SpaceBetween else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showLogo) {
                Image(
                    painter = painterResource(
                        if (isSystemInDarkTheme()) {
                            DmsIcon.SymbolDark
                        } else {
                            DmsIcon.SymbolLight
                        },
                    ),
                    contentDescription = null,
                )
            }
            onBackClick?.let {
                DmsIconButton(
                    resource = DmsIcon.Backward,
                    tint = DmsTheme.colorScheme.scrim,
                    onClick = it,
                )
            }
            actions?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    content = it,
                )
            }
        }
        title?.let {
            Text(
                text = it,
                style = DmsTheme.typography.bodyB,
                color = DmsTheme.colorScheme.onBackground,
            )
        }
    }
}
