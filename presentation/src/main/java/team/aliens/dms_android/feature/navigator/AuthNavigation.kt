package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.auth.changepassword.EditPasswordScreen
import team.aliens.dms_android.feature.auth.changepassword.ResetPasswordScreen
import team.aliens.dms_android.feature.auth.changepassword.UserVerification
import team.aliens.dms_android.feature.auth.comparepassword.ComparePasswordScreen
import team.aliens.dms_android.feature.auth.findid.FindIdScreen
import team.aliens.dms_android.feature.auth.login.SignInScreen
import team.aliens.dms_android.feature.mypage.MyPageChangePasswordScreen

fun NavGraphBuilder.authNavigation(
    navController: NavController,
){
    navigation(
        startDestination = DmsRoute.Auth.SignIn,
        route = DmsRoute.Auth.route,
    ){
        composable(DmsRoute.Auth.SignIn) {
            SignInScreen(
                navController = navController,
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
}