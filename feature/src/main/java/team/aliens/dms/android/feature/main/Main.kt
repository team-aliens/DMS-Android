// TODO: migrate to compose destinations

package team.aliens.dms.android.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.DmsCalendarScaffold
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.R
import team.aliens.dms.android.core.designsystem.slideInFromEnd
import team.aliens.dms.android.core.designsystem.slideInFromStart
import team.aliens.dms.android.core.designsystem.slideOutFromEnd
import team.aliens.dms.android.core.designsystem.slideOutFromStart
import team.aliens.dms.android.core.designsystem.typography.NotoSansFamily
import team.aliens.dms.android.core.designsystem.typography.toSp
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.feature.main.announcement.AnnouncementListScreen
import team.aliens.dms.android.feature.main.application.ApplicationScreen
import team.aliens.dms.android.feature.main.home.HomeScreen
import team.aliens.dms.android.feature.main.mypage.MyPageScreen
import team.aliens.dms.android.feature.main.navigation.MainNavigator
import team.aliens.dms.android.shared.date.util.today

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
internal fun Main(
    modifier: Modifier = Modifier,
    mainNavigator: MainNavigator,
) {
    val (selectedCalendarDate, onSelectedCalendarDateChange) = remember { mutableStateOf(today) }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        ),
    )
    val navController = rememberNavController()
    DmsCalendarScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        selectedDate = selectedCalendarDate,
        onSelectedDateChange = onSelectedCalendarDateChange,
    ) {
        DmsScaffold(
            modifier = Modifier.fillMaxSize(),
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
                    val scope = rememberCoroutineScope()

                    HomeScreen(
                        onShowCalendar = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                        selectedCalendarDate = selectedCalendarDate,
                        onSelectedCalendarDateChange = onSelectedCalendarDateChange,
                        onNavigateToAnnouncementList = { navController.navigateTo(MainSections.ANNOUNCEMENT_LIST.route) }
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
                    ApplicationScreen(
                        onNavigateToStudyRoomList = { mainNavigator.openStudyRoomList() },
                        onNavigateToRemains = { mainNavigator.openRemainsApplication() },
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
                    AnnouncementListScreen(
                        onNavigateToNoticeDetails = mainNavigator::openNoticeDetails,
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
                    MyPageScreen(
                        modifier = Modifier,
                        onNavigateToEditProfileImage = mainNavigator::openEditProfileImage,
                        onNavigateToPointHistory = mainNavigator::openPointHistory,
                        onNavigateToEditPassword = mainNavigator::openEditPasswordNav,
                        onNavigateToUnauthorizedNav = mainNavigator::openUnauthorizedNav,
                    )
                }
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
        contentPadding = PaddingValues(
            top = PaddingDefaults.ExtraSmall,
            start = PaddingDefaults.Small,
            end = PaddingDefaults.Small,
        ),
    ) {
        MainSections.entries.forEach { section ->
            val selected = currentRoute == section.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigateTo(section.route) },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = section.iconRes),
                        contentDescription = stringResource(id = section.labelRes),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = section.labelRes),
                        style = bottomAppBarLabelTextStyle,
                    )
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

@Stable
private val bottomAppBarLabelTextStyle: TextStyle
    @Composable inline get() = TextStyle(
        fontFamily = NotoSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.dp.toSp(),
        lineHeight = 16.sp,
    )

private fun NavController.navigateTo(route: String) = navigate(route) {
    popUpTo(this@navigateTo.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
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
        iconRes = R.drawable.ic_my_page,
        labelRes = team.aliens.dms.android.feature.R.string.bottom_nav_my_page,
    ),
    ;
}
