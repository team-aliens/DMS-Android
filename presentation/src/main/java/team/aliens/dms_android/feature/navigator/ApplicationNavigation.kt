package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import java.util.UUID
import team.aliens.dms_android.feature.remain.RemainApplicationScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomDetailScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomListScreen

fun NavGraphBuilder.applicationNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.StudyRoom,
        route = NavigationRoute.Application,
    ){
        composable(
            route = NavigationRoute.StudyRoomDetail,
            arguments = listOf(
                navArgument("seatId") { type = NavType.StringType },
                navArgument("timeSlot") { type = NavType.StringType },
            ),
        ) {
            val roomId = it.arguments!!.getString("seatId")
            val timeSlot = it.arguments!!.getString("timeSlot")
            if (roomId != null) {
                StudyRoomDetailScreen(
                    navController = navController,
                    roomId = roomId,
                    timeSlot = UUID.fromString(timeSlot),
                )
            }
        }

        composable(NavigationRoute.StudyRoom) {
            StudyRoomListScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.RemainApplication) {
            RemainApplicationScreen(
                navController = navController,
            )
        }
    }
}