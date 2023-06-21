package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import java.util.UUID
import team.aliens.dms_android.common.UuidType
import team.aliens.dms_android.feature.main.editpassword.ConfirmPasswordScreen
import team.aliens.dms_android.feature.main.home.Home
import team.aliens.dms_android.feature.main.home.mypage.pointhistory.PointHistoryScreen
import team.aliens.dms_android.feature.main.home.notice.NoticeDetailsScreen
import team.aliens.dms_android.feature.main.remains.RemainsApplicationScreen
import team.aliens.dms_android.feature.main.studyroom.StudyRoomDetailsScreen
import team.aliens.dms_android.feature.main.studyroom.StudyRoomsScreen

internal object MainNavigation {
    const val route = "main"

    const val Home = "home"
    const val StudyRooms = "studyRooms"
    const val StudyRoomDetails = "studyRoomDetails/{seatId}/{timeSlot}"
    const val NoticeDetails = "noticeDetails/{noticeId}"
    const val RemainsApplication = "remainsApplication"
    const val PointHistory = "pointHistory"
    const val UploadProfileImage = "uploadProfileImage"

    object EditPasswordNavigation {
        const val route = "editPassword"

        const val ConfirmPassword = "confirmPassword"
        const val SetPassword = "setPassword"
    }

    object Arguments {
        const val StudyRoomId = "study-room-id"
        const val Timeslot = "timeslot"
        const val NoticeId = "notice-id"
    }
}

fun NavGraphBuilder.mainNavigation(
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (noticeId: UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPasswordNav: () -> Unit,
    onNavigateToAuthNav: () -> Unit,
    onPrevious: () -> Unit,
    onNavigateToStudyRoomDetails: (
        seatId: UUID,
        studyRoomAvailableTimeId: UUID,
    ) -> Unit,
) {
    navigation(
        startDestination = MainNavigation.Home,
        route = MainNavigation.route,
    ) {
        composable(MainNavigation.Home) {
            Home(
                onNavigateToStudyRooms = onNavigateToStudyRooms,
                onNavigateToRemainsApplication = onNavigateToRemainsApplication,
                onNavigateToNoticeDetails = onNavigateToNoticeDetails,
                onNavigateToUploadProfileImageWithTakingPhoto = onNavigateToUploadProfileImageWithTakingPhoto,
                onNavigateToUploadProfileImageWithSelectingPhoto = onNavigateToUploadProfileImageWithSelectingPhoto,
                onNavigateToPointHistory = onNavigateToPointHistory,
                onNavigateToEditPasswordNav = onNavigateToEditPasswordNav,
                onNavigateToAuthNav = onNavigateToAuthNav,
            )
        }
        composable(MainNavigation.StudyRooms) {
            StudyRoomsScreen(
                onPrevious = onPrevious,
                onNavigateToStudyRoomDetails = onNavigateToStudyRoomDetails,
            )
        }
        composable(
            route = MainNavigation.StudyRoomDetails,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.StudyRoomId) {
                    type = NavType.UuidType
                },
                navArgument(MainNavigation.Arguments.Timeslot) {
                    type = NavType.UuidType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val roomId = getString(MainNavigation.Arguments.StudyRoomId)
                val timeslot = getString(MainNavigation.Arguments.Timeslot)

                StudyRoomDetailsScreen(
                    onPrevious = onPrevious,
                    roomId = UUID.fromString(roomId),
                    timeSlot = UUID.fromString(timeslot),
                )
            }
        }
        composable(
            route = MainNavigation.NoticeDetails,
            arguments = listOf(
                navArgument(MainNavigation.Arguments.NoticeId) {
                    type = NavType.UuidType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val noticeId = getString(MainNavigation.Arguments.NoticeId)
                NoticeDetailsScreen(
                    onPrevious = onPrevious,
                    noticeId = UUID.fromString(noticeId),
                )
            }
        }
        composable(MainNavigation.RemainsApplication) {
            RemainsApplicationScreen(
                onPrevious = onPrevious,
            )
        }
        composable(MainNavigation.PointHistory) {
            PointHistoryScreen(
                onPrevious = onPrevious,
            )
        }
        composable(MainNavigation.UploadProfileImage) {

        }
        editPasswordNavigation()
    }
}

private fun NavGraphBuilder.editPasswordNavigation() {
    navigation(
        startDestination = MainNavigation.EditPasswordNavigation.ConfirmPassword,
        route = MainNavigation.EditPasswordNavigation.route,
    ) {
        composable(MainNavigation.EditPasswordNavigation.ConfirmPassword) {

        }
        composable(MainNavigation.EditPasswordNavigation.SetPassword) {

        }
    }
}
