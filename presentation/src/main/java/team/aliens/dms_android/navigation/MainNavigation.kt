package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import java.util.UUID
import team.aliens.dms_android.common.UuidType
import team.aliens.dms_android.feature.main.home.Home
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
    const val EditPassword = "editPassword"
    const val UploadProfileImage = "uploadProfileImage"

    object Arguments {
        const val RoomId = "room-id"
        const val Timeslot = "timeslot"
    }
}

fun NavGraphBuilder.mainNavigation(
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (noticeId: UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPassword: () -> Unit,
    onNavigateToSignIn: () -> Unit,
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
                onNavigateToEditPassword = onNavigateToEditPassword,
                onNavigateToSignIn = onNavigateToSignIn,
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
                navArgument(MainNavigation.Arguments.RoomId) {
                    type = NavType.UuidType
                },
                navArgument(MainNavigation.Arguments.Timeslot) {
                    type = NavType.UuidType
                },
            ),
        ) { backStackEntry ->
            backStackEntry.arguments?.run {
                val roomId = getString(MainNavigation.Arguments.RoomId)
                val timeslot = getString(MainNavigation.Arguments.Timeslot)

                StudyRoomDetailsScreen(
                    onPrevious = onPrevious,
                    roomId = UUID.fromString(roomId),
                    timeSlot = UUID.fromString(timeslot),
                )
            }
        }
        composable(MainNavigation.NoticeDetails) {

        }
        composable(MainNavigation.RemainsApplication) {

        }
        composable(MainNavigation.PointHistory) {

        }
        composable(MainNavigation.EditPassword) {

        }
        composable(MainNavigation.UploadProfileImage) {

        }
    }
}
