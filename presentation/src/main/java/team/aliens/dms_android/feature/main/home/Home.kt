package team.aliens.dms_android.feature.main.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.util.UUID
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.BottomNavItemLabel
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.feature.main.home.NavigationItemsWrapper.navigationItems
import team.aliens.dms_android.feature.main.home.application.ApplicationScreen
import team.aliens.dms_android.feature.main.home.meal.MealScreen
import team.aliens.dms_android.feature.main.home.mypage.MyPageScreen
import team.aliens.dms_android.feature.main.home.notice.NoticesScreen

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun Home(
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPassword: () -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    val bottomNavController = rememberAnimatedNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()

    val availableFeatures = LocalAvailableFeatures.current
    val studyRoomServiceEnabled by remember(availableFeatures) { mutableStateOf(availableFeatures.studyRoomService) }
    val remainsServiceEnabled by remember(availableFeatures) { mutableStateOf(availableFeatures.remainsService) }
    val pointServiceEnabled by remember(availableFeatures) { mutableStateOf(availableFeatures.pointService) }

    val containsApplicationScreen by remember { mutableStateOf(studyRoomServiceEnabled || remainsServiceEnabled) }

    LaunchedEffect(containsApplicationScreen) {
        if (!containsApplicationScreen) navigationItems.remove(HomeBottomNavigationItem.Application)
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
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
        AnimatedNavHost(
            navController = bottomNavController,
            startDestination = HomeBottomNavigationItem.Meal.route,
        ) {
            composable(
                route = HomeBottomNavigationItem.Meal.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        HomeBottomNavigationItem.Application.route, HomeBottomNavigationItem.Notice.route, HomeBottomNavigationItem.MyPage.route -> fadeIn(
                            animationSpec = tween(
                                durationMillis = 30,
                                delayMillis = 40,
                            ),
                        ) + slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.Right,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                            },
                        )

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        HomeBottomNavigationItem.Application.route, HomeBottomNavigationItem.Notice.route, HomeBottomNavigationItem.MyPage.route -> fadeOut(
                            animationSpec = tween(
                                durationMillis = 30,
                            ),
                        ) + slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Left,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                                delayMillis = 1_0
                            },
                        )

                        else -> null
                    }
                },
            ) {
                MealScreen(
                    onNavigateToNoticeScreen = {
                        bottomNavController.navigateTo(HomeBottomNavigationItem.Notice.route)
                    },
                )
            }
            if (containsApplicationScreen) {
                composable(
                    route = HomeBottomNavigationItem.Application.route,
                    enterTransition = {
                        when (initialState.destination.route) {
                            HomeBottomNavigationItem.Meal.route -> fadeIn(
                                animationSpec = tween(
                                    durationMillis = 80,
                                    delayMillis = 40,
                                ),
                            ) + slideIntoContainer(
                                towards = AnimatedContentScope.SlideDirection.Left,
                                animationSpec = keyframes {
                                    durationMillis = 8_0
                                },
                            )

                            HomeBottomNavigationItem.Notice.route, HomeBottomNavigationItem.MyPage.route -> fadeIn(
                                animationSpec = tween(
                                    durationMillis = 80,
                                    delayMillis = 40,
                                ),
                            ) + slideIntoContainer(
                                towards = AnimatedContentScope.SlideDirection.Right,
                                animationSpec = keyframes {
                                    durationMillis = 8_0
                                },
                            )

                            else -> null
                        }
                    },
                    exitTransition = {
                        when (targetState.destination.route) {
                            HomeBottomNavigationItem.Meal.route -> fadeOut(
                                animationSpec = tween(
                                    durationMillis = 30,
                                ),
                            ) + slideOutOfContainer(
                                towards = AnimatedContentScope.SlideDirection.Right,
                                animationSpec = keyframes {
                                    durationMillis = 8_0
                                    delayMillis = 1_0
                                },
                            )

                            HomeBottomNavigationItem.Notice.route, HomeBottomNavigationItem.MyPage.route -> fadeOut(
                                animationSpec = tween(
                                    durationMillis = 30,
                                ),
                            ) + slideOutOfContainer(
                                towards = AnimatedContentScope.SlideDirection.Left,
                                animationSpec = keyframes {
                                    durationMillis = 8_0
                                    delayMillis = 1_0
                                },
                            )

                            else -> null
                        }
                    },
                ) {
                    ApplicationScreen(
                        onNavigateToStudyRooms =/* todo {
                            navController.navigate(DmsRoute.Home.StudyRooms)
                        }*/onNavigateToStudyRooms,
                        onNavigateToRemainsApplication = /* todo {
                            navController.navigate(DmsRoute.Home.RemainsApplication)
                        }*/onNavigateToRemainsApplication,
                        studyRoomServiceEnabled = studyRoomServiceEnabled,
                        remainsServiceEnabled = remainsServiceEnabled,
                    )
                }
            }
            composable(
                route = HomeBottomNavigationItem.Notice.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        HomeBottomNavigationItem.Meal.route, HomeBottomNavigationItem.Application.route -> fadeIn(
                            animationSpec = tween(
                                durationMillis = 80,
                                delayMillis = 40,
                            ),
                        ) + slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.Left,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                            },
                        )

                        HomeBottomNavigationItem.MyPage.route -> fadeIn(
                            animationSpec = tween(
                                durationMillis = 80,
                                delayMillis = 40,
                            ),
                        ) + slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.Right,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                            },
                        )

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        HomeBottomNavigationItem.Meal.route, HomeBottomNavigationItem.Application.route -> fadeOut(
                            animationSpec = tween(
                                durationMillis = 30,
                            ),
                        ) + slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Right,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                                delayMillis = 1_0
                            },
                        )

                        HomeBottomNavigationItem.MyPage.route -> fadeOut(
                            animationSpec = tween(
                                durationMillis = 30,
                            ),
                        ) + slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Left,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                                delayMillis = 1_0
                            },
                        )

                        else -> null
                    }
                },
            ) {
                NoticesScreen(
                    onNavigateToNoticeDetailsScreen = onNavigateToNoticeDetails,
                    /* todo
                        { value: UUID ->
                            navController.navigate("noticeDetails/${value}")
                        }
                    */
                )
            }
            composable(
                route = HomeBottomNavigationItem.MyPage.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        HomeBottomNavigationItem.Meal.route, HomeBottomNavigationItem.Application.route, HomeBottomNavigationItem.Notice.route -> fadeIn(
                            animationSpec = tween(
                                durationMillis = 80,
                                delayMillis = 40,
                            ),
                        ) + slideIntoContainer(
                            towards = AnimatedContentScope.SlideDirection.Left,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                            },
                        )

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        HomeBottomNavigationItem.Meal.route, HomeBottomNavigationItem.Application.route, HomeBottomNavigationItem.Notice.route -> fadeOut(
                            animationSpec = tween(
                                durationMillis = 30,
                            ),
                        ) + slideOutOfContainer(
                            towards = AnimatedContentScope.SlideDirection.Right,
                            animationSpec = keyframes {
                                durationMillis = 8_0
                                delayMillis = 1_0
                            },
                        )

                        else -> null
                    }
                },
            ) {
                MyPageScreen(
                    onNavigateToUploadProfileImageWithTakingPhoto = onNavigateToUploadProfileImageWithTakingPhoto,
                    /* todo {
                                            navController.navigate(
                                                DmsRoute.Home.UploadProfileImage + "/${SelectImageType.TAKE_PHOTO.ordinal}",
                                            )
                                        }*/
                    onNavigateToUploadProfileImageWithSelectingPhoto = onNavigateToUploadProfileImageWithSelectingPhoto,
                    /* todo {
                                            navController.navigate(
                                                DmsRoute.Home.UploadProfileImage + "/${SelectImageType.SELECT_FROM_GALLERY.ordinal}",
                                            )
                                        }*/
                    onNavigateToPointHistory = onNavigateToPointHistory, /* todo { navController.navigate(DmsRoute.Home.PointHistory) } */
                    onNavigateToEditPassword = onNavigateToEditPassword, /* todo { navController.navigate(DmsRoute.Auth.EditPassword) }, */
                    onNavigateToSignIn = onNavigateToSignIn,/* todo { navController.navigateToSignIn() } */
                    pointServiceEnabled = pointServiceEnabled,
                )
            }
        }
    }
}

@Immutable
private object NavigationItemsWrapper {
    val navigationItems: MutableList<HomeBottomNavigationItem> = mutableListOf(
        HomeBottomNavigationItem.Meal,
        HomeBottomNavigationItem.Application,
        HomeBottomNavigationItem.Notice,
        HomeBottomNavigationItem.MyPage,
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
