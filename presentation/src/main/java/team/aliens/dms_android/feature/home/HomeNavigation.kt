package team.aliens.dms_android.feature.home

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import java.util.UUID
import team.aliens.dms_android.constans.Extra
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.home.mypage.pointhistory.PointHistoryScreen
import team.aliens.dms_android.feature.home.notice.NoticeDetailsScreen
import team.aliens.dms_android.feature.image.UploadProfileImageScreen
import team.aliens.dms_android.feature.remains.RemainsApplicationScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomDetailsScreen
import team.aliens.dms_android.feature.studyroom.StudyRoomsScreen
import team.aliens.dms_android.util.SelectImageType

internal fun NavGraphBuilder.homeNavigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    navigation(
        startDestination = DmsRoute.Home.Main,
        route = DmsRoute.Home.route,
    ) {
        composable(DmsRoute.Home.Main) {
            Home(
                // todo 함수들 다 빼야함!!
                navController = navController,
                scaffoldState = scaffoldState,
            )
        }

        composable(DmsRoute.Home.PointHistory) {
            PointHistoryScreen(
                onBackToMyPage = {
                    navController.popBackStack()
                },
            )
        }

        composable(
            route = DmsRoute.Home.UploadProfileImage + "/{${Extra.selectedImageType}}",
            arguments = listOf(
                navArgument(Extra.selectedImageType) {
                    defaultValue = SelectImageType.SELECT_FROM_GALLERY.ordinal
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->

            val selectImageType = navBackStackEntry.arguments?.getInt(Extra.selectedImageType)
                ?: SelectImageType.SELECT_FROM_GALLERY.ordinal

            UploadProfileImageScreen(
                selectImageType = selectImageType,
                navController = navController,
            )
        }

        composable(
            route = DmsRoute.Home.StudyRoomDetails,
            arguments = listOf(
                navArgument(Extra.seatId) { type = NavType.StringType },
                navArgument(Extra.timeSlot) { type = NavType.StringType },
            ),
        ) {
            val roomId = it.arguments!!.getString(Extra.seatId)
            val timeSlot = it.arguments!!.getString(Extra.timeSlot)
            if (roomId != null) {
                StudyRoomDetailsScreen(
                    onPrevious = {
                        navController.popBackStack()
                    },
                    roomId = roomId,
                    timeSlot = UUID.fromString(timeSlot),
                )
            }
        }

        composable(DmsRoute.Home.StudyRooms) {
            StudyRoomsScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.Home.RemainsApplication) {
            RemainsApplicationScreen(
                onPrevious = {
                    navController.popBackStack()
                },
            )
        }

        composable(
            route = DmsRoute.Home.NoticeDetails,
            arguments = listOf(
                navArgument(Extra.noticeId) { type = NavType.StringType },
            ),
        ) {
            val noticeId = UUID.fromString(it.arguments!!.getString(Extra.noticeId))
            NoticeDetailsScreen(
                navController = navController,
                noticeId = noticeId,
            )
        }
    }
}