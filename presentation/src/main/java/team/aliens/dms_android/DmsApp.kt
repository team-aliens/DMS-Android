package team.aliens.dms_android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.design_system.toast.DormToastLayout
import team.aliens.dms_android.common.AvailableFeaturesWrapper
import team.aliens.dms_android.common.LocalAvailableFeatures
import team.aliens.dms_android.common.rememberAvailableFeatures
import team.aliens.dms_android.extension.navigateToAuthNav
import team.aliens.dms_android.extension.navigateToEditPasswordNav
import team.aliens.dms_android.extension.navigateToEditPasswordSetPassword
import team.aliens.dms_android.extension.navigateToFindId
import team.aliens.dms_android.extension.navigateToHome
import team.aliens.dms_android.extension.navigateToNoticeDetails
import team.aliens.dms_android.extension.navigateToPointHistory
import team.aliens.dms_android.extension.navigateToRemainsApplication
import team.aliens.dms_android.extension.navigateToResetPasswordEnterEmailVerificationCode
import team.aliens.dms_android.extension.navigateToResetPasswordNav
import team.aliens.dms_android.extension.navigateToResetPasswordSetPassword
import team.aliens.dms_android.extension.navigateToSignUpNav
import team.aliens.dms_android.extension.navigateToStudyRoomDetails
import team.aliens.dms_android.extension.navigateToStudyRooms
import team.aliens.dms_android.extension.navigateToUploadProfileImageWithSelectingPhoto
import team.aliens.dms_android.extension.navigateToUploadProfileImageWithTakingPhoto
import team.aliens.dms_android.feature.auth.AuthNavigation
import team.aliens.dms_android.feature.auth.authNavigation
import team.aliens.dms_android.feature.main.MainNavigation
import team.aliens.dms_android.feature.main.mainNavigation
import team.aliens.domain.model.student.Features

@Composable
internal fun DmsApp(
    modifier: Modifier = Modifier,
    isSignedIn: Boolean,
    initialAvailableFeatures: Features,
    dmsAppState: DmsAppState = rememberDmsAppState(
        availableFeatures = rememberAvailableFeatures(initialAvailableFeatures),
    ),
) {
    val navController = dmsAppState.navController
    val toastState = dmsAppState.toastState

    CompositionLocalProvider(
        LocalAvailableFeatures provides AvailableFeaturesWrapper.of(initialAvailableFeatures),
    ) {
        DormToastLayout(
            modifier = modifier.fillMaxSize(),
            toastState = toastState,
        ) {
            NavHost(
                modifier = modifier.fillMaxSize(),
                navController = navController,
                startDestination = if (isSignedIn) MainNavigation.route else AuthNavigation.route,
            ) {
                mainNavigation(
                    onNavigateToStudyRooms = navController::navigateToStudyRooms,
                    onNavigateToRemainsApplication = navController::navigateToRemainsApplication,
                    onNavigateToNoticeDetails = navController::navigateToNoticeDetails,
                    onNavigateToUploadProfileImageWithTakingPhoto = navController::navigateToUploadProfileImageWithTakingPhoto,
                    onNavigateToUploadProfileImageWithSelectingPhoto = navController::navigateToUploadProfileImageWithSelectingPhoto,
                    onNavigateToPointHistory = navController::navigateToPointHistory,
                    onNavigateToEditPasswordNav = navController::navigateToEditPasswordNav,
                    onNavigateToAuthNav = navController::navigateToAuthNav,
                    onPrevious = navController::popBackStack,
                    onNavigateToStudyRoomDetails = navController::navigateToStudyRoomDetails,
                    onNavigateToEditPasswordSetPassword = navController::navigateToEditPasswordSetPassword,
                    onNavigateToHome = navController::navigateToHome,
                )
                authNavigation(
                    onNavigateToHome = navController::navigateToHome,
                    onNavigateToSignUpNav = navController::navigateToSignUpNav,
                    onNavigateToFindId = navController::navigateToFindId,
                    onNavigateToResetPasswordNav = navController::navigateToResetPasswordNav,
                    onNavigateToSignIn = navController::navigateToAuthNav,
                    onNavigateToResetPasswordEnterEmailVerificationCode = navController::navigateToResetPasswordEnterEmailVerificationCode,
                    onNavigateToResetPasswordSetPassword = navController::navigateToResetPasswordSetPassword,
                    onPrevious = navController::popBackStack,
                )
            }
        }
    }
}
