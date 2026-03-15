package team.aliens.dms.android.app.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.labelB

private val bottomMenus = listOf(
    BottomMenu.Home,
    BottomMenu.Application,
    BottomMenu.MyPage,
)

@Composable
fun BottomNavigationBar(
    currentScreen: NavKey?,
    onNavigate: (NavKey) -> Unit,
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        containerColor = DmsTheme.colorScheme.surfaceTint,
    ) {
        bottomMenus.forEach { destination ->
            val selected = currentScreen == destination.route
            val color by animateColorAsState(
                targetValue = if (selected) {
                    DmsTheme.colorScheme.inverseOnSurface
                } else {
                    DmsTheme.colorScheme.scrim
                },
            )

            NavigationBarItem(
                selected = selected,
                enabled = !selected,
                onClick = {
                    onNavigate(destination.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                ),
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = if (selected) destination.selectedIcon else destination.icon),
                            contentDescription = destination.title,
                            tint = color,
                        )
                        Text(
                            text = destination.title,
                            style = DmsTheme.typography.labelB,
                            color = color,
                        )
                    }
                },
            )
        }
    }
}
