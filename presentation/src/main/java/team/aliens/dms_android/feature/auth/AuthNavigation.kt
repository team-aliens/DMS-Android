package team.aliens.dms_android.feature.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.auth.findid.FindIdScreen
import team.aliens.dms_android.feature.auth.resetpassword.resetPasswordNavigation
import team.aliens.dms_android.feature.auth.signin.SignInScreen

internal object AuthNavigation {
    const val route = "auth"

    const val SignIn = this.route + "/signIn"
    const val FindId = this.route + "/findId"
}

fun NavGraphBuilder.authNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToSignUpNav: () -> Unit,
    onNavigateToFindId: () -> Unit,
    onNavigateToResetPasswordNav: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToResetPasswordEnterEmailVerificationCode: () -> Unit,
    onNavigateToResetPasswordSetPassword: () -> Unit,
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
        // todo signUpNavigation()
    }
}
