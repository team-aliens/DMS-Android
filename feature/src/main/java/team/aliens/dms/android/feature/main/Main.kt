package team.aliens.dms.android.feature.main

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.typography.BottomNavItemLabel
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature._legacy.util.OneDay
import team.aliens.dms.android.feature.main.HomeBottomNavigationItem.Announcement
import team.aliens.dms.android.feature.main.HomeBottomNavigationItem.Application
import team.aliens.dms.android.feature.main.HomeBottomNavigationItem.Home
import team.aliens.dms.android.feature.main.HomeBottomNavigationItem.MyPage
import team.aliens.dms.android.feature.main.navigation.MainNavigator
import java.util.Date

internal fun Date.plusOneDay(): Date {
    return Date(this.time.plus(OneDay))
}

internal fun Date.minusOneDay(): Date {
    return Date(this.time.minus(OneDay))
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun Main(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    /*
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPasswordNav: () -> Unit,
    onNavigateToAuthNav: () -> Unit,
    onNavigateToNotificationBox: () -> Unit,*/
) {/*
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    var currentCalendarDate by rememberSaveable { mutableStateOf(Now()) }
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
        ) {  paddingValues ->
            *//*NavHost(
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
                            Application.route, Announcement.route, MyPage.route -> slideInFromStart()
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Application.route, Announcement.route, MyPage.route -> slideOutFromEnd()
                            else -> null
                        }
                    },
                ) {
                    HomeScreen(
                        calendarDate = currentCalendarDate,
                        onNextDay = { onCalendarDateChange(currentCalendarDate.plusOneDay()) },
                        onPreviousDay = { onCalendarDateChange(currentCalendarDate.minusOneDay()) },
                        onShowCalendar = { coroutineScope.launch { bottomSheetState.show() } },
                        onNavigateToNoticeScreen = { bottomNavController.navigateTo(Announcement.route) },
                        onNavigateToNotificationBox = onNavigateToNotificationBox,
                    )
                }
                if (containsApplicationScreen) {
                    composable(
                        route = Application.route,
                        enterTransition = {
                            when (targetState.destination.route) {
                                Home.route -> slideInFromEnd()
                                Announcement.route, MyPage.route -> slideInFromStart()
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                Home.route -> slideOutFromStart()
                                Announcement.route, MyPage.route -> slideOutFromEnd()
                                else -> null
                            }
                        },
                    ) {
                        ApplicationScreen(
                            modifier = Modifier.padding(paddingValues),
                            onNavigateToStudyRooms = onNavigateToStudyRooms,
                            onNavigateToRemainsApplication = onNavigateToRemainsApplication,
                            studyRoomServiceEnabled = studyRoomServiceEnabled,
                            remainsServiceEnabled = remainsServiceEnabled,
                        )
                    }
                }
                composable(
                    route = Announcement.route,
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
                    AnnouncementListScreen(
                        modifier = Modifier.padding(paddingValues),
                        onNavigateToNoticeDetails = onNavigateToNoticeDetails,
                    )
                }
                composable(
                    route = MyPage.route,
                    enterTransition = {
                        when (targetState.destination.route) {
                            Home.route, Application.route, Announcement.route -> slideInFromEnd()
                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            Home.route, Application.route, Announcement.route -> slideOutFromStart()
                            else -> null
                        }
                    },
                ) {
                    MyPageScreen(
                        modifier = Modifier.padding(paddingValues),
                        onNavigateToUploadProfileImageWithTakingPhoto = onNavigateToUploadProfileImageWithTakingPhoto,
                        onNavigateToUploadProfileImageWithSelectingPhoto = onNavigateToUploadProfileImageWithSelectingPhoto,
                        onNavigateToPointHistory = onNavigateToPointHistory,
                        onNavigateToEditPasswordNav = onNavigateToEditPasswordNav,
                        onNavigateToAuthNav = onNavigateToAuthNav,
                        pointServiceEnabled = pointServiceEnabled,
                    )
                }
            }*//*
        }
    }*/
}

@Stable
private object NavigationItemsWrapper {
    val navigationItems: MutableList<HomeBottomNavigationItem> = mutableListOf(
        Home,
        Application,
        Announcement,
        MyPage,
    )
}

private val selectedColor: Color
    @Composable get() = DmsTheme.colorScheme.onSurface

private val unselectedColor: Color
    @Composable get() = DmsTheme.colorScheme.line

@Composable
private fun BottomNavBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    navigationItems: List<HomeBottomNavigationItem>,
) {
    BottomNavigation(
        backgroundColor = DmsTheme.colorScheme.surface,
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


internal sealed class HomeBottomNavigationItem(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int,
) {
    object Home : HomeBottomNavigationItem(
        route = "home",
        iconResId = R.drawable.ic_home,
        titleResId = R.string.bottom_nav_home,
    )

    object Application : HomeBottomNavigationItem(
        route = "application",
        iconResId = R.drawable.ic_application,
        titleResId = R.string.bottom_nav_application,
    )

    object Announcement : HomeBottomNavigationItem(
        route = "announcement",
        iconResId = R.drawable.ic_notice,
        titleResId = R.string.bottom_nav_announcement,
    )

    object MyPage : HomeBottomNavigationItem(
        route = "mypage",
        iconResId = R.drawable.ic_mypage,
        titleResId = R.string.bottom_nav_my_page,
    )
}
