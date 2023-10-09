package team.aliens.dms_android.feature.feature.editpassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

internal object EditPasswordNavigation {
    const val route = "editPassword"

    const val ConfirmPassword = route + "/confirmPassword"
    const val SetPassword = route + "/setPassword"
}

internal fun NavGraphBuilder.editPasswordNavigation(
    onNavigateToEditPasswordSetPassword: () -> Unit,
    onPrevious: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    navigation(
        startDestination = EditPasswordNavigation.ConfirmPassword,
        route = EditPasswordNavigation.route,
    ) {
        composable(EditPasswordNavigation.ConfirmPassword) {
            ConfirmPasswordScreen(
                onNavigateToEditPasswordSetPassword = onNavigateToEditPasswordSetPassword,
                onPrevious = onPrevious
            )
        }
        composable(EditPasswordNavigation.SetPassword) {
            EditPasswordSetPasswordScreen(
                onNavigateToHome = onNavigateToHome,
            )
        }
    }
}
