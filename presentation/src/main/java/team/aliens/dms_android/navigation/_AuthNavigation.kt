package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

internal object AuthNavigation {
    const val route = "auth"

    const val SignIn = "signIn"
    const val FindId = "findId"
    const val ResetPassword = "resetPassword"
}

fun NavGraphBuilder.authNavigation() {
    navigation(
        startDestination = AuthNavigation.SignIn,
        route = AuthNavigation.route,
    ) {
        composable(AuthNavigation.SignIn) {

        }
        composable(AuthNavigation.FindId) {

        }
        composable(AuthNavigation.ResetPassword) {

        }
        signUpNavigation()
    }
}

private fun NavGraphBuilder.signUpNavigation() {
}

/*
internal fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
) {
    composable(DmsRoute.Auth.SignIn) {
        SignInScreen(
            onNavigateToHome = { navController.navigateToHome() },
            onNavigateToSignUp = { navController.navigate(DmsRoute.SignUp.route) },
            onNavigateToFindId = { navController.navigate(DmsRoute.Auth.FindId) },
            onNavigateToResetPassword = { navController.navigate(DmsRoute.Auth.ResetPassword) },
        )
    }

    composable(DmsRoute.Auth.EditPassword) {
        EditPasswordScreen(
            navController = navController,
        )
    }

    composable(
        route = DmsRoute.Auth.ComparePassword,
    ) {
        ComparePasswordScreen(
            navController = navController,
        )
    }

    composable(DmsRoute.Auth.FindId) {
        FindIdScreen(
            navController = navController,
        )
    }

    composable(DmsRoute.Auth.UserVerification) {
        UserVerification(
            navController = navController,
        )
    }

    composable(DmsRoute.Auth.ResetPassword) {
        ResetPasswordScreen(
            navController = navController,
        )
    }

    composable(DmsRoute.Home.MyPageChangePassword) {
        MyPageChangePasswordScreen(
            navController = navController,
        )
    }
}*/
