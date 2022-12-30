package com.example.dms_android.feature.navigator

import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.design_system.color.DormColor
import com.example.dms_android.feature.auth.login.LoginScreen
import com.example.dms_android.feature.notice.NoticeDetailScreen
import com.example.dms_android.feature.pointlist.PointListScreen
import com.example.dms_android.feature.studyroom.StudyRoomDetailScreen

@Composable
fun RootDms(
    route: String
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    NavHost(navController = navController, startDestination = route) {
        composable(
            route = NavigationRoute.Login
        ) {
            LoginScreen(scaffoldState = scaffoldState, navController = navController)
        }
        composable(NavigationRoute.Main) {
            DmsApp(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(
            route = "noticeDetail/{noticeId}",
            arguments = listOf(navArgument("noticeId") { type = NavType.StringType })
        ) {
            val noticeId = it.arguments!!.getString("noticeId")
            if (noticeId != null) {
                NoticeDetailScreen(navController, noticeId)
            }
        }
        composable(NavigationRoute.PointList) {
            PointListScreen(
                navController = navController,
            )
        }
        composable(
            route = "studyRoomDetail/{seatId}",
            arguments = listOf(
                navArgument("seatId") { type = NavType.StringType },
            )
        ) {
            val roomId = it.arguments!!.getString("seatId")
            if (roomId != null) {
                StudyRoomDetailScreen(navController = navController, roomId)
            }
        }
    }
}