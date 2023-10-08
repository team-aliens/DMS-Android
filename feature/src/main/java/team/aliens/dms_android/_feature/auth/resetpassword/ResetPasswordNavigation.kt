package team.aliens.dms_android._feature.auth.resetpassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

internal object ResetPasswordNavigation {
    const val route = "resetPassword"

    const val IdVerification = this.route + "/passwordIdVerification"
    const val EnterEmailVerificationCode = this.route + "/passwordEnterEmailVerificationCode"
    const val SetPassword = this.route + "/passwordSetPassword"
}

internal fun NavGraphBuilder.resetPasswordNavigation(
    onNavigateToResetPasswordEnterEmailVerificationCode: () -> Unit,
    onNavigateToResetPasswordSetPassword: () -> Unit,
    onPrevious: () -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    navigation(
        startDestination = ResetPasswordNavigation.IdVerification,
        route = ResetPasswordNavigation.route,
    ) {
        composable(ResetPasswordNavigation.IdVerification) {
            ResetPasswordIdVerificationScreen(
                onNavigateToResetPasswordEnterEmailVerificationCode = onNavigateToResetPasswordEnterEmailVerificationCode,
            )
        }
        composable(ResetPasswordNavigation.EnterEmailVerificationCode) {
            ResetPasswordEnterEmailVerificationCodeScreen(
                onNavigateToResetPasswordSetPassword = onNavigateToResetPasswordSetPassword
            )
        }
        composable(ResetPasswordNavigation.SetPassword) {
            ResetPasswordSetPasswordScreen(
                onPrevious = onPrevious,
                onNavigateToSignIn = onNavigateToSignIn,
            )
        }
    }
}
