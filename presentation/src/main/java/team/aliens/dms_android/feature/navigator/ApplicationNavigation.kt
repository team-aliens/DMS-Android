package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import java.util.UUID
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.remain.RemainApplicationScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomDetailScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomListScreen

fun NavGraphBuilder.applicationNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.Application.StudyRoom,
        route = NavigationRoute.Application.name,
    ){
        composable(
            route = NavigationRoute.Application.StudyRoomDetail,
            arguments = listOf(
                navArgument(Extra.seatId) { type = NavType.StringType },
                navArgument(Extra.timeSlot) { type = NavType.StringType },
            ),
        ) {
            val roomId = it.arguments!!.getString(Extra.seatId)
            val timeSlot = it.arguments!!.getString(Extra.timeSlot)
            if (roomId != null) {
                StudyRoomDetailScreen(
                    navController = navController,
                    roomId = roomId,
                    timeSlot = UUID.fromString(timeSlot),
                )
            }
        }

        composable(NavigationRoute.Application.StudyRoom) {
            StudyRoomListScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Application.RemainApplication) {
            RemainApplicationScreen(
                navController = navController,
            )
        }
    }
}