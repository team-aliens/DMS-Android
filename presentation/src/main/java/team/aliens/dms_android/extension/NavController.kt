package team.aliens.dms_android.extension

import androidx.navigation.NavHostController
import java.util.UUID
import team.aliens.dms_android.feature.auth.AuthNavigation
import team.aliens.dms_android.feature.auth.resetpassword.ResetPasswordNavigation
import team.aliens.dms_android.feature.main.MainNavigation
import team.aliens.dms_android.feature.main.editpassword.EditPasswordNavigation
import team.aliens.dms_android.util.SelectImageType

internal fun NavHostController.navigateToHome() {
    this.navigate(MainNavigation.route) {
        launchSingleTop = true
        popUpTo(this@navigateToHome.graph.startDestinationId) {
            saveState = true
        }
    }
}

internal fun NavHostController.navigateToAuthNav() {
    this.navigate(AuthNavigation.route) {
        popUpTo(this@navigateToAuthNav.graph.id) {
            inclusive = true
        }
    }
}

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

internal fun NavHostController.navigateToEditPasswordNav() {
    this.navigate(EditPasswordNavigation.route)
}

internal fun NavHostController.navigateToStudyRoomDetails(
    seatId: UUID,
    studyRoomAvailableTime: UUID,
) {
    this.navigate(MainNavigation.StudyRoomDetails + "/${seatId}/${studyRoomAvailableTime}")
}

internal fun NavHostController.navigateToEditPasswordSetPassword() {
    this.navigate(EditPasswordNavigation.SetPassword)
}

// Auth
internal fun NavHostController.navigateToSignUpNav() {
    // todo this.navigate(SignUpNavigation.route)
}

internal fun NavHostController.navigateToFindId() {
    this.navigate(AuthNavigation.FindId)
}

internal fun NavHostController.navigateToResetPasswordNav() {
    this.navigate(ResetPasswordNavigation.route)
}

internal fun NavHostController.navigateToResetPasswordEnterEmailVerificationCode() {
    this.navigate(ResetPasswordNavigation.EnterEmailVerificationCode)
}

internal fun NavHostController.navigateToResetPasswordSetPassword() {
    this.navigate(ResetPasswordNavigation.SetPassword)
}
