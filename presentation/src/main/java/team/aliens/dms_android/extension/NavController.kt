package team.aliens.dms_android.extension

import androidx.navigation.NavHostController
import java.util.UUID
import team.aliens.dms_android.feature.auth.AuthNavigation
import team.aliens.dms_android.feature.auth.resetpassword.ResetPasswordNavigation
import team.aliens.dms_android.feature.main.MainNavigation
import team.aliens.dms_android.feature.main.editpassword.EditPasswordNavigation
import team.aliens.dms_android.util.SelectImageType

internal infix fun String.appendNavArgumentName(value: String): String {
    return this.plus("/{$value}")
}

internal fun String.appendNavArgumentName(vararg values: String): String {
    return this.apply { values.forEach(this::appendNavArgumentName) }
}

internal infix fun String.appendNavArgument(value: Any): String {
    return this.plus("/$value")
}

internal fun String.appendNavArgument(vararg values: Any): String {
    return this.apply { values.forEach(this::appendNavArgument) }
}

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

internal fun NavHostController.navigateToRemainsApplication() {
    this.navigate(MainNavigation.RemainsApplication)
}

internal fun NavHostController.navigateToNoticeDetails(noticeId: UUID) {
    this.navigate(MainNavigation.NoticeDetails appendNavArgument noticeId)
}

internal fun NavHostController.navigateToUploadProfileImage(
    selectImageType: SelectImageType,
) {
    this.navigate(MainNavigation.UploadProfileImage appendNavArgument selectImageType.name)
}

internal fun NavHostController.navigateToUploadProfileImageWithTakingPhoto() {
    this.navigateToUploadProfileImage(SelectImageType.TAKE_PHOTO)
}

internal fun NavHostController.navigateToUploadProfileImageWithSelectingPhoto() {
    this.navigateToUploadProfileImage(SelectImageType.SELECT_FROM_GALLERY)
}

internal fun NavHostController.navigateToPointHistory() {
    this.navigate(MainNavigation.PointHistory)
}

internal fun NavHostController.navigateToEditPasswordNav() {
    this.navigate(EditPasswordNavigation.route)
}

internal fun NavHostController.navigateToStudyRoomDetails(
    seatId: UUID,
    timeslot: UUID,
) {
    this.navigate(MainNavigation.StudyRoomDetails.appendNavArgument(seatId, timeslot))
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
