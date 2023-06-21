package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

internal object MainNavigation {
    const val route = "main"

    const val Home = "home"
    const val StudyRoomDetails = "studyRoomDetails/{seatId}/{timeSlot}"
    const val NoticeDetails = "noticeDetails/{noticeId}"
    const val RemainsApplication = "remainsApplication"
    const val PointHistory = "pointHistory"
    const val EditPassword = "editPassword"
    const val UploadProfileImage = "uploadProfileImage"
}

fun NavGraphBuilder.mainNavigation() {
    navigation(
        startDestination = MainNavigation.Home,
        route = MainNavigation.route,
    ) {
        composable(MainNavigation.Home) {

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