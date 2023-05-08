package team.aliens.dms_android.feature.navigator

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import java.util.UUID
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.image.ConfirmImageScreen
import team.aliens.dms_android.feature.notice.NoticeDetailScreen
import team.aliens.dms_android.feature.pointlist.PointListScreen
import team.aliens.dms_android.feature.remain.RemainApplicationScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomDetailScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomListScreen
import team.aliens.dms_android.util.SelectImageType

fun NavGraphBuilder.homeNavigation(
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    navigation(
        startDestination = DmsRoute.Home.Main,
        route = DmsRoute.Home.route,
    ) {
        composable(DmsRoute.Home.Main) {
            DmsApp(
                navController = navController,
                scaffoldState = scaffoldState,
            )
        }

        composable(DmsRoute.Home.PointList) {
            PointListScreen(
                navController = navController,
            )
        }

        composable(
            route = DmsRoute.Home.UploadImage + "/{${Extra.selectedImageType}}",
            arguments = listOf(
                navArgument(Extra.selectedImageType) {
                    defaultValue = SelectImageType.SELECT_FROM_GALLERY.ordinal
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->

            val selectImageType = navBackStackEntry.arguments?.getInt(Extra.selectedImageType)
                ?: SelectImageType.SELECT_FROM_GALLERY.ordinal

            ConfirmImageScreen(
                selectImageType = selectImageType,
                navController = navController,
            )
        }

        composable(
            route = DmsRoute.Home.StudyRoomDetail,
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

        composable(DmsRoute.Home.StudyRoomList) {
            StudyRoomListScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.Home.RemainApplication) {
            RemainApplicationScreen(
                navController = navController,
            )
        }

        composable(
            route = DmsRoute.Home.NoticeDetail,
            arguments = listOf(
                navArgument(Extra.noticeId) { type = NavType.StringType },
            ),
        ) {
            val noticeId = it.arguments!!.getString(Extra.noticeId)
            if (noticeId != null) {
                NoticeDetailScreen(
                    navController = navController,
                    noticeId = noticeId,
                )
            }
        }
    }
}