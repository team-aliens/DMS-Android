// TODO: migrate to compose destinations

package team.aliens.dms.android.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.slideInFromEnd
import team.aliens.dms.android.core.designsystem.slideInFromStart
import team.aliens.dms.android.core.designsystem.slideOutFromEnd
import team.aliens.dms.android.core.designsystem.slideOutFromStart
import team.aliens.dms.android.feature.main.navigation.MainNavigator

@RootNavGraph(start = true)
@Destination
@Composable
internal fun Main(
    modifier: Modifier = Modifier,
    mainNavigator: MainNavigator,
) {
    val navController = rememberNavController()
    DmsScaffold(
        modifier = modifier.background(Color.Cyan),
        bottomBar = {
            DmsBottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = MainSections.HOME.route,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 80)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 80)) },
        ) {
            composable(
                route = MainSections.HOME.route,
                enterTransition = {
                    when (targetState.destination.route) {
                        MainSections.APPLICATION.route, MainSections.ANNOUNCEMENT_LIST.route, MainSections.MY_PAGE.route -> slideInFromStart()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        MainSections.APPLICATION.route, MainSections.ANNOUNCEMENT_LIST.route, MainSections.MY_PAGE.route -> slideOutFromEnd()
                        else -> null
                    }
                },
            ) {
                Box(
                    modifier = Modifier
                        .background(color = DmsTheme.colorScheme.error)
                        .fillMaxSize(),
                )
            }

            composable(
                route = MainSections.APPLICATION.route,
                enterTransition = {
                    when (targetState.destination.route) {
                        MainSections.HOME.route -> slideInFromEnd()
                        MainSections.ANNOUNCEMENT_LIST.route, MainSections.MY_PAGE.route -> slideInFromStart()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        MainSections.HOME.route -> slideOutFromStart()
                        MainSections.ANNOUNCEMENT_LIST.route, MainSections.MY_PAGE.route -> slideOutFromEnd()
                        else -> null
                    }
                },
            ) {
                Box(
                    modifier = Modifier
                        .background(color = DmsTheme.colorScheme.primary)
                        .fillMaxSize(),
                )
            }

            composable(
                route = MainSections.ANNOUNCEMENT_LIST.route,
                enterTransition = {
                    when (targetState.destination.route) {
                        MainSections.HOME.route, MainSections.APPLICATION.route -> slideInFromEnd()
                        MainSections.MY_PAGE.route -> slideInFromStart()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        MainSections.HOME.route, MainSections.APPLICATION.route -> slideOutFromStart()
                        MainSections.MY_PAGE.route -> slideOutFromEnd()
                        else -> null
                    }
                },
            ) {
                Box(
                    modifier = Modifier
                        .background(color = DmsTheme.colorScheme.line)
                        .fillMaxSize(),
                )
            }
            composable(
                route = MainSections.MY_PAGE.route,
                enterTransition = {
                    when (targetState.destination.route) {
                        MainSections.HOME.route, MainSections.APPLICATION.route, MainSections.ANNOUNCEMENT_LIST.route -> slideInFromEnd()
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        MainSections.HOME.route, MainSections.APPLICATION.route, MainSections.ANNOUNCEMENT_LIST.route -> slideOutFromStart()
                        else -> null
                    }
                },
            ) {
                Box(
                    modifier = Modifier
                        .background(color = DmsTheme.colorScheme.onPrimaryContainer)
                        .fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun DmsBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    BottomAppBar(
        containerColor = DmsTheme.colorScheme.surface,
        modifier = modifier.graphicsLayer {
            clip = true
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
            )
            shadowElevation = 20f
        },
    ) {
        MainSections.entries.forEach { section ->
            val selected = currentRoute == section.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(section.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = section.iconRes),
                        contentDescription = stringResource(id = section.labelRes),
                    )
                },
                label = {
                    Text(text = stringResource(id = section.labelRes))
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DmsTheme.colorScheme.onSurface,
                    selectedTextColor = DmsTheme.colorScheme.onSurface,
                    indicatorColor = DmsTheme.colorScheme.surface,
                    unselectedIconColor = DmsTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = DmsTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}

@Immutable
private enum class MainSections(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
) {
    HOME(
        route = "home",
        iconRes = R.drawable.ic_home,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_home,
    ),
    APPLICATION(
        route = "application",
        iconRes = R.drawable.ic_applicate,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_application,
    ),
    ANNOUNCEMENT_LIST(
        route = "announcement_list",
        iconRes = R.drawable.ic_notice,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_announcement_list,
    ),
    MY_PAGE(
        route = "my_page",
        iconRes = R.drawable.ic_mypage,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_my_page,
    ),
    ;
}
