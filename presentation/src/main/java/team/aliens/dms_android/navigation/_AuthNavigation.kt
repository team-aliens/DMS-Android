package team.aliens.dms_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.auth.changepassword.EditPasswordScreen
import team.aliens.dms_android.feature.auth.changepassword.ResetPasswordScreen
import team.aliens.dms_android.feature.auth.changepassword.UserVerification
import team.aliens.dms_android.feature.main.comparepassword.ComparePasswordScreen
import team.aliens.dms_android.feature.auth.findid.FindIdScreen
import team.aliens.dms_android.feature.auth.signin.SignInScreen
import team.aliens.dms_android.feature.main.home.mypage.MyPageChangePasswordScreen
import team.aliens.dms_android.navigateToHome

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
}