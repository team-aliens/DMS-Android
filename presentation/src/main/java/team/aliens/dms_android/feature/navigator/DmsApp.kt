package team.aliens.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.typography.BottomNavItemLabel
import team.aliens.dms_android.feature.application.ApplicationScreen
import team.aliens.dms_android.feature.cafeteria.CafeteriaScreen
import team.aliens.dms_android.feature.mypage.MyPageScreen
import team.aliens.dms_android.feature.notice.NoticeScreen
import team.aliens.presentation.R

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
            BottomNavBar(
                navController = navHostController,
                navBackStackEntry = navBackStackEntry,
            )
        },
        modifier = Modifier.background(DormColor.Gray900),
    ) {
        NavHost(
            navController = navHostController,
            startDestination = BottomNavigationItem.Meal.route,
        ) {
            composable(BottomNavigationItem.Meal.route) {
                CafeteriaScreen(
                    navController = navHostController,
                )
            }
            composable(BottomNavigationItem.Application.route) {
                ApplicationScreen(
                    navController = navController,
                    // onBackPress = onBackPressedInOtherPages,
                )
            }
            composable(BottomNavigationItem.Notice.route) {
                NoticeScreen(
                    navController = navController,
                    //onBackPress = onBackPressedInOtherPages,
                )
            }
            composable(BottomNavigationItem.MyPage.route) {
                MyPageScreen(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    //onBackPress = onBackPressedInOtherPages,
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
) {

    val currentDestination = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .height(60.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp,
                )
            ),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = DormColor.Gray900,
    ) {
        val selectedColor = DormColor.Gray900
        val unselectedColor = DormColor.Gray500

        val selected = when (currentDestination) {
            BottomNavigationItem.Meal.route -> 1
            BottomNavigationItem.Application.route -> 2
            BottomNavigationItem.Notice.route -> 3
            else -> 4
        }

        // Cafeteria
        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(64.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Meal.route,
                    navController,
                )
            },
            label = {
                BottomNavItemLabel(
                    text = stringResource(id = R.string.Home),
                    color = if (selected == 1) selectedColor
                    else unselectedColor
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.Meal.iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            selected = selected == 1,
            unselectedContentColor = unselectedColor,
        )

        // Application
        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(64.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Application.route,
                    navController,
                )
            },
            label = {
                BottomNavItemLabel(
                    text = stringResource(id = R.string.Application),
                    color = if (selected == 2) selectedColor
                    else unselectedColor
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.Application.iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            selected = selected == 2,
            unselectedContentColor = unselectedColor,
        )

        // Notice
        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(64.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Notice.route,
                    navController,
                )
            },
            label = {
                BottomNavItemLabel(
                    text = stringResource(id = R.string.Guide),
                    color = if (selected == 3) selectedColor
                    else unselectedColor
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.Notice.iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            selected = selected == 3,
            unselectedContentColor = unselectedColor,
        )

        // My Page
        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(64.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.MyPage.route,
                    navController,
                )
            },
            label = {
                BottomNavItemLabel(
                    text = stringResource(id = R.string.MyPage),
                    color = if (selected == 4) selectedColor
                    else unselectedColor
                )
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.MyPage.iconResId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            selected = selected == 4,
            unselectedContentColor = unselectedColor,
        )
    }
}

fun navigateBottomNavigation(
    route: String,
    navController: NavHostController,
) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }

        launchSingleTop = true
        restoreState = true
    }
}
