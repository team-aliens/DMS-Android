package team.aliens.dms_android.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import java.util.UUID
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.BottomNavItemLabel
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.home.NavigationItemsWrapper.navigationItems
import team.aliens.dms_android.feature.home.application.ApplicationScreen
import team.aliens.dms_android.feature.home.meal.MealScreen
import team.aliens.dms_android.feature.home.mypage.MyPageScreen
import team.aliens.dms_android.feature.home.notice.NoticesScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()

    val availableFeatures = LocalAvailableFeatures.current
    val studyRoomServiceEnabled by remember(availableFeatures) {
        mutableStateOf(availableFeatures[Extra.isStudyRoomEnabled]!!)
    }
    val remainsServiceEnabled by remember(availableFeatures) {
        mutableStateOf(availableFeatures[Extra.isRemainServiceEnabled]!!)
    }
    val containsApplicationScreen = studyRoomServiceEnabled || remainsServiceEnabled

    LaunchedEffect(containsApplicationScreen) {
        if (!containsApplicationScreen)
            navigationItems.remove(BottomNavigationItem.Application)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavBar(
                navController = bottomNavController,
                navBackStackEntry = navBackStackEntry,
                navigationItems = navigationItems,
            )
        },
        modifier = Modifier.background(
            DormTheme.colors.background,
        ),
        backgroundColor = DormTheme.colors.background,
        contentColor = DormTheme.colors.background,
    ) {
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavigationItem.Meal.route,
        ) {
            composable(BottomNavigationItem.Meal.route) {
                MealScreen(
                    onNavigateToNoticeScreen = {
                        bottomNavController.navigateTo(BottomNavigationItem.Notice.route)
                    },
                )
            }
            if (containsApplicationScreen) {
                composable(BottomNavigationItem.Application.route) {
                    ApplicationScreen(
                        onNavigateToStudyRooms = {
                            navController.navigate(DmsRoute.Home.StudyRooms)
                        },
                        onNavigateToRemainsApplication = {
                            navController.navigate(DmsRoute.Home.RemainsApplication)
                        },
                        studyRoomServiceEnabled = studyRoomServiceEnabled,
                        remainsServiceEnabled = remainsServiceEnabled,
                    )
                }
            }
            composable(BottomNavigationItem.Notice.route) {
                NoticesScreen(
                    onNavigateToNoticeDetailsScreen = { value: UUID ->
                        navController.navigate("noticeDetails/${value}")
                    },
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
    val navigationItems: MutableList<BottomNavigationItem> = mutableListOf(
        BottomNavigationItem.Meal,
        BottomNavigationItem.Application,
        BottomNavigationItem.Notice,
        BottomNavigationItem.MyPage,
    )
}

private val selectedColor: Color
    @Composable get() = DormTheme.colors.onSurface

private val unselectedColor: Color
    @Composable get() = DormTheme.colors.primaryVariant

@Composable
private fun BottomNavBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    navigationItems: List<BottomNavigationItem>,
) {

    BottomNavigation(
        backgroundColor = DormTheme.colors.surface,
        modifier = Modifier.graphicsLayer {
            clip = true
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
            )
            shadowElevation = 20f
        },
    ) {
        navigationItems.forEach { navigationItem ->
            val currentDestination = navBackStackEntry?.destination?.route
            val selected = navigationItem.route == currentDestination

            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigateTo(navigationItem.route)
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

private fun NavHostController.navigateTo(
    route: String,
) {
    this.navigate(route) {
        popUpTo(this@navigateTo.graph.findStartDestination().id) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }
}
