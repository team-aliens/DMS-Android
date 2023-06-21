package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

internal object AuthNavigation {
    const val route = "auth"

    const val SignIn = "signIn"
    const val FindId = "findId"

    // todo move to reset password package
    object ResetPasswordNavigation {
        const val route = "resetPassword"

        const val IdVerification = "idVerification"
        const val EnterEmailVerificationCode = "enterEmailVerificationCode"
        const val SetPassword = "setPassword"
    }

    // todo move to sign up package
    object SignUpNavigation {
        const val route = "signUp"

        const val EnterSchoolVerificationCode = "enterSchoolVerificationCode"
        const val EnterSchoolVerificationQuestion = "enterSchoolVerificationQuestion"
        const val SetEmail = "setEmail"
        const val SetId = "setId"
        const val SetPassword = "setPassword"
        const val SetProfile = "setProfile"
        const val Terms = "terms"
    }
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
        resetPasswordNavigation()
        signUpNavigation()
    }
}

private fun NavGraphBuilder.resetPasswordNavigation() {
    navigation(
        startDestination = AuthNavigation.ResetPasswordNavigation.IdVerification,
        route = AuthNavigation.ResetPasswordNavigation.route,
    ) {
        composable(AuthNavigation.ResetPasswordNavigation.IdVerification) {

        }
        composable(AuthNavigation.ResetPasswordNavigation.EnterEmailVerificationCode) {

        }
        composable(AuthNavigation.ResetPasswordNavigation.SetPassword) {

        }
    }
}

private fun NavGraphBuilder.signUpNavigation() {
    navigation(
        startDestination = AuthNavigation.SignUpNavigation.EnterSchoolVerificationCode,
        route = AuthNavigation.SignUpNavigation.route,
    ) {
        composable(AuthNavigation.SignUpNavigation.EnterSchoolVerificationCode) {

        }
        composable(AuthNavigation.SignUpNavigation.EnterSchoolVerificationQuestion) {

        }
        composable(AuthNavigation.SignUpNavigation.SetEmail) {

        }
        composable(AuthNavigation.SignUpNavigation.SetId) {

        }
        composable(AuthNavigation.SignUpNavigation.SetPassword) {

        }
        composable(AuthNavigation.SignUpNavigation.SetProfile) {

        }
        composable(AuthNavigation.SignUpNavigation.Terms) {

        }
    }
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
