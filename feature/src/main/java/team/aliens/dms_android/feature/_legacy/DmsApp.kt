package team.aliens.dms_android.feature._legacy

/*

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
            toastState = toastState,
        ) {
            NavHost(
                modifier = modifier.fillMaxSize(),
                navController = navController,
                startDestination = if (isSignedIn) MainNavigation.route else AuthNavigation.route,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
                exitTransition = { fadeOut(tween(delayMillis = 10)) },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(delayMillis = 10),
                    )
                }
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
                    onPrevious = navController::navigateUp,
                    onNavigateToStudyRoomDetails = navController::navigateToStudyRoomDetails,
                    onNavigateToEditPasswordSetPassword = navController::navigateToEditPasswordSetPassword,
                    onNavigateToHome = navController::navigateToHome,
                    onNavigateToNotificationBox = navController::navigateToNotificationBox,
                )
                authNavigation(
                    onNavigateToHome = navController::navigateToHome,
                    onNavigateToSignUpNav = navController::navigateToSignUpNav,
                    onNavigateToFindId = navController::navigateToFindId,
                    onNavigateToResetPasswordNav = navController::navigateToResetPasswordNav,
                    onNavigateToSignIn = navController::navigateToAuthNav,
                    onNavigateToResetPasswordEnterEmailVerificationCode = navController::navigateToResetPasswordEnterEmailVerificationCode,
                    onNavigateToResetPasswordSetPassword = navController::navigateToResetPasswordSetPassword,
                    onNavigateToEnterSchoolVerificationQuestion = navController::navigateToEnterSchoolVerificationQuestion,
                    onNavigateToSetEmail = navController::navigateToSetEmail,
                    onNavigateToVerifyEmail = navController::navigateToVerifyEmail,
                    onNavigateToSetId = navController::navigateToSetId,
                    onNavigateToSetPassword = navController::navigateToSetPassword,
                    onNavigateToSetProfile = navController::navigateToSetProfile,
                    onNavigateToTerms = navController::navigateToTerms,
                    onNavigateToSignInWithInclusive = navController::navigateToSignInWithInclusive,
                    onNavigateToSetEmailWithInclusive = navController::navigateToSetEmailWithInclusive,
                    onPrevious = navController::navigateUp,
                )
            }
        }
    }
}
*/
