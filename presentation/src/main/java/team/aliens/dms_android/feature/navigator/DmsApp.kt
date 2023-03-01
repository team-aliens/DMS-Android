package team.aliens.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

    val bottomTabSelectedItem = rememberSaveable {
        mutableStateOf(BottomNavigationItem.Meal.route)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavBar(
                navController = navHostController,
                bottomTabSelectedItem = bottomTabSelectedItem,
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
                    bottomTabSelectedItem = bottomTabSelectedItem,
                )
            }
            composable(BottomNavigationItem.Application.route) { ApplicationScreen(navController = navController) }
            composable(BottomNavigationItem.Notice.route) { NoticeScreen(navController = navController) }
            composable(BottomNavigationItem.MyPage.route) {
                MyPageScreen(navController = navController, scaffoldState = scaffoldState)
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    bottomTabSelectedItem: MutableState<String>,
) = BottomAppBar(
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

    val selected = when (bottomTabSelectedItem.value) {
        BottomNavigationItem.Meal.route -> 1
        BottomNavigationItem.Application.route -> 2
        BottomNavigationItem.Notice.route -> 3
        else -> 4
    }

    BottomNavigationItem(
        modifier = Modifier
            .weight(0.5f)
            .size(64.dp),
        onClick = {
            navigateBottomNavigation(BottomNavigationItem.Meal.route, navController)
            bottomTabSelectedItem.value = BottomNavigationItem.Meal.route
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

    BottomNavigationItem(
        modifier = Modifier
            .weight(0.5f)
            .size(64.dp),
        onClick = {
            navigateBottomNavigation(BottomNavigationItem.Application.route, navController)
            bottomTabSelectedItem.value = BottomNavigationItem.Application.route
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

    BottomNavigationItem(
        modifier = Modifier
            .weight(0.5f)
            .size(64.dp),
        onClick = {
            navigateBottomNavigation(BottomNavigationItem.Notice.route, navController)
            bottomTabSelectedItem.value = BottomNavigationItem.Notice.route
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

    BottomNavigationItem(
        modifier = Modifier
            .weight(0.5f)
            .size(64.dp),
        onClick = {
            navigateBottomNavigation(BottomNavigationItem.MyPage.route, navController)
            bottomTabSelectedItem.value = BottomNavigationItem.MyPage.route
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

fun navigateBottomNavigation(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
