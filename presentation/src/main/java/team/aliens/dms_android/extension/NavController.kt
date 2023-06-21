package team.aliens.dms_android.extension

import androidx.navigation.NavHostController
import java.util.UUID
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.navigation.MainNavigation
import team.aliens.dms_android.util.SelectImageType

// Main
internal fun NavHostController.navigateToStudyRooms() {
    this.navigate(MainNavigation.StudyRooms)
}

internal fun NavHostController.navigateToStudyRoomDetails() {
    this.navigate(MainNavigation.StudyRoomDetails)
}

internal fun NavHostController.navigateToRemainsApplication() {
    this.navigate(MainNavigation.RemainsApplication)
}

internal fun NavHostController.navigateToNoticeDetails(noticeId: UUID) {
    this.navigate(MainNavigation.NoticeDetails + "/${noticeId}")
}

internal fun NavHostController.navigateToUploadProfileImageWithTakingPhoto() {
    this.navigate(MainNavigation.UploadProfileImage + "/${SelectImageType.TAKE_PHOTO.ordinal}")
}

internal fun NavHostController.navigateToUploadProfileImageWithSelectingPhoto() {
    this.navigate(MainNavigation.UploadProfileImage + "/${SelectImageType.SELECT_FROM_GALLERY.ordinal}")
}

internal fun NavHostController.navigateToPointHistory() {
    this.navigate(MainNavigation.PointHistory)
}

internal fun NavHostController.navigateToEditPassword() {
    this.navigate(MainNavigation.EditPassword)
}

internal fun NavHostController.navigateToSignIn() {
    this.navigate(DmsRoute.Auth.route) {
        popUpTo(this@navigateToSignIn.graph.id) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToHome() {
    this.navigate(DmsRoute.Home.route) {
        launchSingleTop = true
        popUpTo(this@navigateToHome.graph.startDestinationId) {
            saveState = true
        }
    }
}
