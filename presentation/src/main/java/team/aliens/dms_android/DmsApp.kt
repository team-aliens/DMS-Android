package team.aliens.dms_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.toast.DormToastLayout
import team.aliens.design_system.toast.rememberToastState
import team.aliens.dms_android.extension.navigateToEditPassword
import team.aliens.dms_android.extension.navigateToNoticeDetails
import team.aliens.dms_android.extension.navigateToPointHistory
import team.aliens.dms_android.extension.navigateToRemainsApplication
import team.aliens.dms_android.extension.navigateToSignIn
import team.aliens.dms_android.extension.navigateToStudyRooms
import team.aliens.dms_android.extension.navigateToUploadProfileImageWithSelectingPhoto
import team.aliens.dms_android.extension.navigateToUploadProfileImageWithTakingPhoto
import team.aliens.dms_android.navigation.authNavigation
import team.aliens.dms_android.navigation.mainNavigation

@Composable
internal fun DmsApp(
    modifier: Modifier = Modifier,
    dmsAppState: DmsAppState = rememberDmsAppState(),
) {
    val scaffoldState = dmsAppState.scaffoldState
    val navController = dmsAppState.navController
    val toastState = rememberToastState()

    DormToastLayout(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = DormTheme.colors.background,
            ),
        toastState = toastState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(
                    paddingValues = paddingValues,
                ),
                navController = navController,
                startDestination = "ERROR",/*initialRoute*/
            ) {
                mainNavigation(
                    onNavigateToStudyRooms = navController::navigateToStudyRooms,
                    onNavigateToRemainsApplication = navController::navigateToRemainsApplication,
                    onNavigateToNoticeDetails = navController::navigateToNoticeDetails,
                    onNavigateToUploadProfileImageWithTakingPhoto = navController::navigateToUploadProfileImageWithTakingPhoto,
                    onNavigateToUploadProfileImageWithSelectingPhoto = navController::navigateToUploadProfileImageWithSelectingPhoto,
                    onNavigateToPointHistory = navController::navigateToPointHistory,
                    onNavigateToEditPassword = navController::navigateToEditPassword,
                    onNavigateToSignIn = navController::navigateToSignIn,
                )
                authNavigation()
            }
        }
    }
}

