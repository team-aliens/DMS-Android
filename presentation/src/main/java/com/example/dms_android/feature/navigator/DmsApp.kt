package com.example.dms_android.feature.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.design_system.color.DormColor
import com.example.dms_android.feature.mypage.MyPageScreen
import com.example.dms_android.feature.notice.NoticeScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DmsApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navHostController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavBar(navController = navHostController) },
        modifier = Modifier
            .background(DormColor.Gray900),
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = BottomNavigationItem.Meal.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(BottomNavigationItem.Meal.route) { /*CafeteriaScreen(navController = navController)*/ }
            composable(BottomNavigationItem.Survey.route) { /*TODO("아직 구현 안됨")*/ }
            composable(BottomNavigationItem.Notice.route) { NoticeScreen(navController = navController) }
            composable(BottomNavigationItem.MyPage.route) { MyPageScreen(navController = navController) }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
) =
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        ),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = DormColor.Gray900,
    ) {
        val bottomTabSelectedItem = rememberSaveable {
            mutableStateOf(BottomNavigationItem.Meal.route)
        }
        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(25.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Meal.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.Meal.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.Meal.iconResId),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.Meal.route
        )

        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(25.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Survey.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.Survey.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.Survey.iconResId),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.Survey.route
        )

        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(25.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.Notice.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.Notice.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.Notice.iconResId),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.Notice.route
        )

        BottomNavigationItem(
            modifier = Modifier
                .weight(0.5f)
                .size(25.dp),
            onClick = {
                navigateBottomNavigation(
                    BottomNavigationItem.MyPage.route,
                    navController
                )
                bottomTabSelectedItem.value = BottomNavigationItem.MyPage.route
            },
            icon = {
                Icon(
                    painter = painterResource(id = BottomNavigationItem.MyPage.iconResId),
                    contentDescription = null
                )
            },
            selected = bottomTabSelectedItem.value == BottomNavigationItem.MyPage.route
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
