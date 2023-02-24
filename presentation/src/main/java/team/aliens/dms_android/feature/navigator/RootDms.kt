package team.aliens.dms_android.feature.navigator

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordScreen
import team.aliens.dms_android.feature.auth.login.LoginScreen
import team.aliens.dms_android.feature.notice.NoticeDetailScreen
import team.aliens.dms_android.feature.pointlist.PointListScreen
import team.aliens.dms_android.feature.remain.RemainApplicationScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomDetailScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomListScreen

@Composable
fun RootDms(
    route: String,
) {

    val navController = rememberNavController()

    val scaffoldState = rememberScaffoldState()

    NavHost(
        navController = navController,
        startDestination = route,
    ) {

        composable(NavigationRoute.Login) {
            LoginScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Main) {
            DmsApp(
                navController = navController,
                scaffoldState = scaffoldState,
            )
        }

        composable(
            route = NavigationRoute.NoticeDetail,
            arguments = listOf(
                navArgument("noticeId") { type = NavType.StringType },
            ),
        ) {
            val noticeId = it.arguments!!.getString("noticeId")
            if (noticeId != null) {
                NoticeDetailScreen(
                    navController = navController,
                    noticeId = noticeId,
                )
            }
        }

        composable(NavigationRoute.PointList) {
            PointListScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.ChangePassword) {
            ChangePasswordScreen()
        }

        composable(
            route = NavigationRoute.StudyRoomDetail,
            arguments = listOf(
                navArgument("seatId") { type = NavType.StringType },
            ),
        ) {
            val roomId = it.arguments!!.getString("seatId")
            if (roomId != null) {
                StudyRoomDetailScreen(
                    navController = navController,
                    roomId = roomId,
                )
            }
        }
        
        composable(NavigationRoute.StudyRoom){
            StudyRoomListScreen(
                navController = navController,
            )
        }
        
        composable(NavigationRoute.RemainApplication){
            RemainApplicationScreen(
                navController = navController,
            )
        }
    }
}