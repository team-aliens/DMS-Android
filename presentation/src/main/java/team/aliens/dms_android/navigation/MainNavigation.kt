package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import java.util.UUID
import team.aliens.dms_android.feature.main.home.Home

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
}

fun NavGraphBuilder.mainNavigation(
    onNavigateToStudyRooms: () -> Unit,
    onNavigateToRemainsApplication: () -> Unit,
    onNavigateToNoticeDetails: (UUID) -> Unit,
    onNavigateToUploadProfileImageWithTakingPhoto: () -> Unit,
    onNavigateToUploadProfileImageWithSelectingPhoto: () -> Unit,
    onNavigateToPointHistory: () -> Unit,
    onNavigateToEditPassword: () -> Unit,
    onNavigateToSignIn: () -> Unit,
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

        }
        composable(MainNavigation.StudyRoomDetails) {

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
