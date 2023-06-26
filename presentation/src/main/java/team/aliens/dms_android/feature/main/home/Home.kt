package team.aliens.dms_android.feature.main.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import java.util.Date
import java.util.UUID
import kotlinx.coroutines.launch
import team.aliens.design_system.animate.slideInFromEnd
import team.aliens.design_system.animate.slideInFromStart
import team.aliens.design_system.animate.slideOutFromEnd
import team.aliens.design_system.animate.slideOutFromStart
import team.aliens.design_system.component.DormCalendarLayout
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.BottomNavItemLabel
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.feature.main.home.HomeBottomNavigationItem.Application
import team.aliens.dms_android.feature.main.home.HomeBottomNavigationItem.Home
import team.aliens.dms_android.feature.main.home.HomeBottomNavigationItem.MyPage
import team.aliens.dms_android.feature.main.home.HomeBottomNavigationItem.Notice
import team.aliens.dms_android.feature.main.home.NavigationItemsWrapper.navigationItems
import team.aliens.dms_android.feature.main.home.application.ApplicationScreen
import team.aliens.dms_android.feature.main.home.home.HomeScreen
import team.aliens.dms_android.feature.main.home.mypage.MyPageScreen
import team.aliens.dms_android.feature.main.home.notice.NoticesScreen

private const val OneDay = 1000 * 60 * 60 * 24

internal fun Date.plusOneDay(): Date {
    return Date(this.time.plus(OneDay))
}

internal fun Date.minusOneDay(): Date {
    return Date(this.time.minus(OneDay))
}

private typealias Today = Date

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun Home(
    modifier: Modifier = Modifier,
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPasswordNav: () -> Unit,
    onNavigateToAuthNav: () -> Unit,
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    var currentCalendarDate by rememberSaveable { mutableStateOf(Today()) }
    val onCalendarDateChange = { newDate: Date -> currentCalendarDate = newDate }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val availableFeatures = LocalAvailableFeatures.current
    val studyRoomServiceEnabled by remember(availableFeatures) { mutableStateOf(availableFeatures.features.mealService) }
    val remainsServiceEnabled by remember(availableFeatures) { mutableStateOf(availableFeatures.features.remainsService) }
    val pointServiceEnabled by remember(availableFeatures) { mutableStateOf(availableFeatures.features.pointService) }

    val containsApplicationScreen by remember { mutableStateOf(studyRoomServiceEnabled || remainsServiceEnabled) }

    LaunchedEffect(containsApplicationScreen) {
        if (!containsApplicationScreen) navigationItems.remove(Application)
    }

    DormCalendarLayout(
        bottomSheetState = bottomSheetState,
        onDateChange = onCalendarDateChange,
        selectedDate = currentCalendarDate,
    ) {
        Scaffold(
            scaffoldState = rememberScaffoldState(),
            bottomBar = {
                BottomNavBar(
                    navController = bottomNavController,
                    navBackStackEntry = navBackStackEntry,
                    navigationItems = navigationItems,
                )
            },
            modifier = modifier.fillMaxSize(),
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = bottomNavController,
                startDestination = Home.route,
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = 80)) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 80)) },
            ) {
                composable(
                    route = Home.route,
                    enterTransition = {
                        when (targetState.destination.route) {
                            Application.route, Notice.route, MyPage.route -> slideInFromStart()
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Application.route, Notice.route, MyPage.route -> slideOutFromEnd()
                            else -> null
                        }
                    },
                ) {
                    HomeScreen(
                        calendarDate = currentCalendarDate,
                        onNextDay = { onCalendarDateChange(currentCalendarDate.plusOneDay()) },
                        onPreviousDay = { onCalendarDateChange(currentCalendarDate.minusOneDay()) },
                        onShowCalendar = { coroutineScope.launch { bottomSheetState.show() } },
                        onNavigateToNoticeScreen = {
                            bottomNavController.navigateTo(Notice.route)
                        },
                    )
                }
                if (containsApplicationScreen) {
                    composable(
                        route = Application.route,
                        enterTransition = {
                            when (targetState.destination.route) {
                                Home.route -> slideInFromEnd()
                                Notice.route, MyPage.route -> slideInFromStart()
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Home.route -> slideOutFromStart()
                                Notice.route, MyPage.route -> slideOutFromEnd()
                                else -> null
                            }
                        },
                    ) {
                        ApplicationScreen(
                            onNavigateToStudyRooms = onNavigateToStudyRooms,
                            onNavigateToRemainsApplication = onNavigateToRemainsApplication,
                            studyRoomServiceEnabled = studyRoomServiceEnabled,
                            remainsServiceEnabled = remainsServiceEnabled,
                        )
                    }
                }
                composable(
                    route = Notice.route,
                    enterTransition = {
                        when (targetState.destination.route) {
                            Home.route, Application.route -> slideInFromEnd()
                            MyPage.route -> slideInFromStart()
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Home.route, Application.route -> slideOutFromStart()
                            MyPage.route -> slideOutFromEnd()
                            else -> null
                        }
                    },
                ) {
                    NoticesScreen(
                        onNavigateToNoticeDetailsScreen = onNavigateToNoticeDetails,
                    )
                }
                composable(
                    route = MyPage.route,
                    enterTransition = {
                        when (targetState.destination.route) {
                            Home.route, Application.route, Notice.route -> slideInFromEnd()
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Home.route, Application.route, Notice.route -> slideOutFromStart()
                            else -> null
                        }
                    },
                ) {
                    MyPageScreen(
                        onNavigateToUploadProfileImageWithTakingPhoto = onNavigateToUploadProfileImageWithTakingPhoto,
                        onNavigateToUploadProfileImageWithSelectingPhoto = onNavigateToUploadProfileImageWithSelectingPhoto,
                        onNavigateToPointHistory = onNavigateToPointHistory,
                        onNavigateToEditPasswordNav = onNavigateToEditPasswordNav,
                        onNavigateToAuthNav = onNavigateToAuthNav,
                        pointServiceEnabled = pointServiceEnabled,
                    )
                }
            }
        }
    }
}

@Stable
private object NavigationItemsWrapper {
    val navigationItems: MutableList<HomeBottomNavigationItem> = mutableListOf(
        Home,
        Application,
        Notice,
        MyPage,
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
    navigationItems: List<HomeBottomNavigationItem>,
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
