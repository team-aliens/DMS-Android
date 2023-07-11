package team.aliens.dms_android.extension

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import java.util.UUID
import team.aliens.dms_android.feature.auth.AuthNavigation
import team.aliens.dms_android.feature.auth.resetpassword.ResetPasswordNavigation
import team.aliens.dms_android.feature.main.MainNavigation
import team.aliens.dms_android.feature.main.editpassword.EditPasswordNavigation
import team.aliens.dms_android.feature.signup.SignUpNavigation
import team.aliens.dms_android.util.SelectImageType

internal infix fun String.appendNavArgumentName(value: String): String {
    return this.plus("/{$value}")
}

internal infix fun String.appendNavArgument(value: Any): String {
    return this.plus("/$value")
}

internal fun NavHostController.popBackStackIfNotStartDestination() {
    val currentDestination = this.currentDestination
    val startDestination = this.graph.findStartDestination()

    if (currentDestination != startDestination) {
        this.popBackStack()
    }
}

internal fun NavHostController.navigateToHome() {
    this.navigate(MainNavigation.route) {
        launchSingleTop = true
        popUpTo(this@navigateToHome.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToSignInWithInclusive(){
    this.navigate(AuthNavigation.SignIn){
        popUpTo(this@navigateToSignInWithInclusive.graph.id){
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToSetEmailWithInclusive(){
    this.navigate(SignUpNavigation.VerifyEmail.SetEmail)
}

internal fun NavHostController.navigateToAuthNav() {
    this.navigate(AuthNavigation.route) {
        popUpTo(this@navigateToAuthNav.graph.id) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateSingleTop(
    route: String,
) {
    this.navigate(route) { launchSingleTop = true }
}

internal fun NavHostController.navigateSingleTop(
    route: String,
    builder: NavOptionsBuilder.() -> Unit,
) {
    this.navigate(route) {
        builder()
        launchSingleTop = true
    }
}

internal fun NavHostController.navigateSingleTopWithRestoringState(
    route: String,
) {
    this.navigateSingleTop(route) {
        restoreState = true
    }
}

internal fun NavHostController.navigateSingleTopWithRestoringState(
    route: String,
    builder: NavOptionsBuilder.() -> Unit,
) {
    this.navigateSingleTopWithRestoringState(route) {
        builder()
        restoreState = true
    }
}

// Main
internal fun NavHostController.navigateToStudyRooms() {
    this.navigateSingleTopWithRestoringState(MainNavigation.StudyRooms)
}

internal fun NavHostController.navigateToRemainsApplication() {
    this.navigateSingleTopWithRestoringState(MainNavigation.RemainsApplication)
}

internal fun NavHostController.navigateToNoticeDetails(noticeId: UUID) {
    this.navigateSingleTopWithRestoringState(MainNavigation.NoticeDetails appendNavArgument noticeId)
}

internal fun NavHostController.navigateToUploadProfileImage(
    selectImageType: SelectImageType,
) {
    this.navigateSingleTop(MainNavigation.UploadProfileImage appendNavArgument selectImageType.name)
}

internal fun NavHostController.navigateToUploadProfileImageWithTakingPhoto() {
    this.navigateToUploadProfileImage(SelectImageType.TAKE_PHOTO)
}

internal fun NavHostController.navigateToUploadProfileImageWithSelectingPhoto() {
    this.navigateToUploadProfileImage(SelectImageType.SELECT_FROM_GALLERY)
}

internal fun NavHostController.navigateToPointHistory() {
    this.navigateSingleTopWithRestoringState(MainNavigation.PointHistory)
}

internal fun NavHostController.navigateToEditPasswordNav() {
    this.navigateSingleTop(EditPasswordNavigation.route)
}

internal fun NavHostController.navigateToStudyRoomDetails(
    seatId: UUID,
    timeslot: UUID,
) {
    this.navigateSingleTopWithRestoringState(
        MainNavigation.StudyRoomDetails appendNavArgument seatId appendNavArgument timeslot,
    )
}

internal fun NavHostController.navigateToEditPasswordSetPassword() {
    this.navigateSingleTop(EditPasswordNavigation.SetPassword)
}

internal fun NavHostController.navigateToNotificationBox() {
    this.navigateSingleTopWithRestoringState(MainNavigation.NotificationBox)
}

// Auth
internal fun NavHostController.navigateToSignUpNav() {
    this.navigateSingleTop(SignUpNavigation.route)
}

internal fun NavHostController.navigateToFindId() {
    this.navigateSingleTop(AuthNavigation.FindId)
}

internal fun NavHostController.navigateToResetPasswordNav() {
    this.navigateSingleTop(ResetPasswordNavigation.route)
}

internal fun NavHostController.navigateToResetPasswordEnterEmailVerificationCode() {
    this.navigateSingleTop(ResetPasswordNavigation.EnterEmailVerificationCode)
}

internal fun NavHostController.navigateToResetPasswordSetPassword() {
    this.navigateSingleTop(ResetPasswordNavigation.SetPassword)
}

internal fun NavHostController.navigateToEnterSchoolVerificationQuestion(){
    this.navigateSingleTop(SignUpNavigation.VerifySchool.EnterSchoolVerificationQuestion)
}

internal fun NavHostController.navigateToSetEmail(){
    this.navigateSingleTop(SignUpNavigation.VerifyEmail.SetEmail)
}

internal fun NavHostController.navigateToVerifyEmail(){
    this.navigateSingleTop(SignUpNavigation.VerifyEmail.VerifyEmail)
}

internal fun NavHostController.navigateToSetId(){
    this.navigateSingleTop(SignUpNavigation.SetUserInformation.SetId)
}

internal fun NavHostController.navigateToSetPassword(){
    this.navigateSingleTop(SignUpNavigation.SetUserInformation.SetPassword)
}

internal fun NavHostController.navigateToSetProfile(){
    this.navigateSingleTop(SignUpNavigation.SetUserInformation.SetProfile)
}

internal fun NavHostController.navigateToTerms(){
    this.navigateSingleTop(SignUpNavigation.Terms)
}
