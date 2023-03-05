package team.aliens.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.BottomNavItemLabel
import team.aliens.dms_android.feature.application.ApplicationScreen
import team.aliens.dms_android.feature.cafeteria.CafeteriaScreen
import team.aliens.dms_android.feature.mypage.MyPageScreen
import team.aliens.dms_android.feature.notice.NoticeScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DmsApp(
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    val navHostController = rememberNavController()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            Column(verticalArrangement = Arrangement.Bottom) {
                Spacer(modifier = Modifier
                    .height(40.dp)
                    .background(Color.Cyan))
                BottomNavBar(
                    navController = navHostController,
                    navBackStackEntry = navBackStackEntry,
                )
            }
        },
        modifier = Modifier.background(
            DormTheme.colors.background,
        ),
    ) {
        NavHost(
            navController = navHostController,
            startDestination = BottomNavigationItem.Meal.route,
        ) {
            composable(BottomNavigationItem.Meal.route) {
                CafeteriaScreen(
                    navController = navHostController,
                    onMoveToNotice = {
                        navHostController.navigate(NavigationRoute.BottomNavigation.Notice)
                    }
                )
            }
            composable(BottomNavigationItem.Application.route) {
                ApplicationScreen(
                    navController = navController,
                )
            }
            composable(BottomNavigationItem.Notice.route) {
                NoticeScreen(
                    navController = navController,
                )
            }
            composable(BottomNavigationItem.MyPage.route) {
                MyPageScreen(
                    navController = navController,
                    scaffoldState = scaffoldState,
                )
            }
        }
    }
}

@Immutable
private object NavigationItemsWrapper {
    val navigationItems: List<BottomNavigationItem> = listOf(
        BottomNavigationItem.Meal,
        BottomNavigationItem.Application,
        BottomNavigationItem.Notice,
        BottomNavigationItem.MyPage,
    )
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
) {

    val currentDestination = navBackStackEntry?.destination?.route

    val selectedColor = DormTheme.colors.onSurface
    val unselectedColor = DormTheme.colors.primaryVariant

    BottomNavigation(
        backgroundColor = DormTheme.colors.background,
        contentColor = DormTheme.colors.onSurface,
        modifier = Modifier.graphicsLayer {
            clip = true
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
            )
            shadowElevation = 20f
        },
    ) {
        NavigationItemsWrapper.navigationItems.forEach { navigationItem ->

            val selected = navigationItem.route == currentDestination

            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navigateTo(
                        navigationItem.route,
                        navController,
                    )
                },
                label = {
                    BottomNavItemLabel(
                        text = stringResource(id = navigationItem.titleResId),
                        color = if (selected) selectedColor
                        else unselectedColor,
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navigationItem.iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected) selectedColor
                        else unselectedColor,
                    )
                },
                unselectedContentColor = unselectedColor,
                selectedContentColor = selectedColor,
                alwaysShowLabel = true,
            )
        }
    }
}

private fun navigateTo(
    route: String,
    navController: NavHostController,
) {
    navController.navigate(
        route = route,
    ) {

        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }

        launchSingleTop = true
        restoreState = true
    }
}