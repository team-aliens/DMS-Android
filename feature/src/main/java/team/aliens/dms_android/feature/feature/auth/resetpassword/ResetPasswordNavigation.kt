package team.aliens.dms_android.feature.feature.auth.resetpassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

internal object ResetPasswordNavigation {
    const val route = "resetPassword"

    const val IdVerification = route + "/passwordIdVerification"
    const val EnterEmailVerificationCode = route + "/passwordEnterEmailVerificationCode"
    const val SetPassword = route + "/passwordSetPassword"
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
