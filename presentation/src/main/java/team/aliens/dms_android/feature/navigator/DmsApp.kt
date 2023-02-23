package team.aliens.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.color.DormColor
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
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = BottomNavigationItem.Meal.route,
            modifier = Modifier.padding(innerPadding),
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
    cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor = MaterialTheme.colors.background,
    contentColor = DormColor.Gray900,
) {
    BottomNavigationItem(modifier = Modifier
        .weight(0.5f)
        .size(25.dp), onClick = {
        navigateBottomNavigation(BottomNavigationItem.Meal.route, navController)
        bottomTabSelectedItem.value = BottomNavigationItem.Meal.route
    }, icon = {
        Icon(
            painter = painterResource(id = BottomNavigationItem.Meal.iconResId),
            contentDescription = null
        )
    }, selected = bottomTabSelectedItem.value == BottomNavigationItem.Meal.route
    )

    BottomNavigationItem(modifier = Modifier
        .weight(0.5f)
        .size(25.dp), onClick = {
        navigateBottomNavigation(BottomNavigationItem.Application.route, navController)
        bottomTabSelectedItem.value = BottomNavigationItem.Application.route
    }, icon = {
        Icon(
            painter = painterResource(id = BottomNavigationItem.Application.iconResId),
            contentDescription = null
        )
    }, selected = bottomTabSelectedItem.value == BottomNavigationItem.Application.route
    )

    BottomNavigationItem(modifier = Modifier
        .weight(0.5f)
        .size(25.dp), onClick = {
        navigateBottomNavigation(BottomNavigationItem.Notice.route, navController)
        bottomTabSelectedItem.value = BottomNavigationItem.Notice.route
    }, icon = {
        Icon(
            painter = painterResource(id = BottomNavigationItem.Notice.iconResId),
            contentDescription = null
        )
    }, selected = bottomTabSelectedItem.value == BottomNavigationItem.Notice.route
    )

    BottomNavigationItem(modifier = Modifier
        .weight(0.5f)
        .size(25.dp), onClick = {
        navigateBottomNavigation(BottomNavigationItem.MyPage.route, navController)
        bottomTabSelectedItem.value = BottomNavigationItem.MyPage.route
    }, icon = {
        Icon(
            painter = painterResource(id = BottomNavigationItem.MyPage.iconResId),
            contentDescription = null
        )
    }, selected = bottomTabSelectedItem.value == BottomNavigationItem.MyPage.route
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
