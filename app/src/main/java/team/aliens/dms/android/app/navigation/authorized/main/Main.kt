package team.aliens.dms.android.app.navigation.authorized.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.scope.DestinationScopeWithNoDependencies
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms.android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.HomeScreenDestination
import team.aliens.dms.android.feature.destinations.MyPageScreenDestination

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
internal fun Main(
    modifier: Modifier = Modifier,
    // navigator: MainSectionNavigator,
) {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()

    DmsScaffold(
        modifier = modifier.background(Color.Cyan),
        bottomBar = {
            DmsBottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
            )
        },
    ) {
        /* DestinationsNavHost(
             modifier = Modifier.padding(padValues),
             engine = engine,
             navGraph = MainNavGraph,
             navController = navController,
             dependenciesContainerBuilder = { dependency(currentNavigator()) },
         )*/
    }
}

private fun DestinationScopeWithNoDependencies<*>.currentNavigator(): MainNavigator =
    MainNavigator(
        // navGraph = navBackStackEntry.destination.navGraph().also { println("GRAPHGRAPH : $it") }, // todo:
        navGraph = AuthorizedNavGraph,
        navController = navController,
    )

private fun NavDestination.navGraph(): NavGraphSpec {
    hierarchy.forEach { destination ->
        MainNavGraph.nestedNavGraphs.forEach { navGraph ->
            if (destination.route == navGraph.route) {
                return navGraph
            }
        }
    }

    throw RuntimeException("Unknown nav graph for destination $route")
}

@Composable
private fun DmsBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    BottomAppBar(
        containerColor = DmsTheme.colorScheme.surface,
        modifier = modifier
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                )
                shadowElevation = 20f
            },
    ) {
        MainSections.entries.forEach { section ->
            val selected = currentDestination == section.direction
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(section.direction) {
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
            )
        }
    }
}

@Immutable
private enum class MainSections(
    val direction: DirectionDestinationSpec,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
) {
    HOME(
        direction = HomeScreenDestination,
        iconRes = R.drawable.ic_home,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_home,
    ),
    APPLICATION(
        direction = ApplicationScreenDestination,
        iconRes = R.drawable.ic_applicate,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_application,
    ),
    ANNOUNCEMENT_LIST(
        direction = AnnouncementListScreenDestination,
        iconRes = R.drawable.ic_notice,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_announcement_list,
    ),
    MY_PAGE(
        direction = MyPageScreenDestination,
        iconRes = R.drawable.ic_mypage,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_my_page,
    ),
    ;
}

/*
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
    }*//*

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
*/
