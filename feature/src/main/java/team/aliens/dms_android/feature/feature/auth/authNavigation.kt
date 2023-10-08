package team.aliens.dms_android.feature.feature.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.feature.auth.findid.FindIdScreen
import team.aliens.dms_android.feature.feature.auth.resetpassword.resetPasswordNavigation
import team.aliens.dms_android.feature.feature.auth.signin.SignInScreen
import team.aliens.dms_android.feature.feature.signup.signUpNavigation

internal object AuthNavigation {
    const val route = "auth"

    const val SignIn = route + "/signIn"
    const val FindId = route + "/findId"
}

internal fun NavGraphBuilder.authNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToSignUpNav: () -> Unit,
    onNavigateToFindId: () -> Unit,
    onNavigateToResetPasswordNav: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToResetPasswordEnterEmailVerificationCode: () -> Unit,
    onNavigateToResetPasswordSetPassword: () -> Unit,
    onNavigateToEnterSchoolVerificationQuestion: () -> Unit,
    onNavigateToSetEmail: () -> Unit,
    onNavigateToVerifyEmail: () -> Unit,
    onNavigateToSetId: () -> Unit,
    onNavigateToSetPassword: () -> Unit,
    onNavigateToSetProfile: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
    onNavigateToSetEmailWithInclusive: () -> Unit,
    onPrevious: () -> Unit,
) {
    navigation(
        startDestination = AuthNavigation.SignIn,
        route = AuthNavigation.route,
    ) {
        composable(AuthNavigation.SignIn) {
            SignInScreen(
                onNavigateToHome = onNavigateToHome,
                onNavigateToSignUpNav = onNavigateToSignUpNav,
                onNavigateToFindId = onNavigateToFindId,
                onNavigateToResetPasswordNav = onNavigateToResetPasswordNav,
            )
        }
        composable(AuthNavigation.FindId) {
            FindIdScreen(
                onNavigateToSignIn = onNavigateToSignIn,
            )
        }
        resetPasswordNavigation(
            onNavigateToResetPasswordEnterEmailVerificationCode = onNavigateToResetPasswordEnterEmailVerificationCode,
            onNavigateToResetPasswordSetPassword = onNavigateToResetPasswordSetPassword,
            onPrevious = onPrevious,
            onNavigateToSignIn = onNavigateToSignIn,
        )
        signUpNavigation(
            onNavigateToEnterSchoolQuestion = onNavigateToEnterSchoolVerificationQuestion,
            onNavigateToSendVerificationEmail = onNavigateToSetEmail,
            onNavigateToVerifyEmail = onNavigateToVerifyEmail,
            onNavigateToSetId = onNavigateToSetId,
            onNavigateToSetPassword = onNavigateToSetPassword,
            onNavigateToSetProfileImage = onNavigateToSetProfile,
            onNavigateToTerms = onNavigateToTerms,
            onNavigateToSignInWithInclusive = onNavigateToSignInWithInclusive,
            onNavigateToSetEmailWithInclusive = onNavigateToSetEmailWithInclusive,
        )
    }
}
