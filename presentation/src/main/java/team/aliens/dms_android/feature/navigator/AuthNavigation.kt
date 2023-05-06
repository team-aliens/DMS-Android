package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordScreen
import team.aliens.dms_android.feature.auth.changepassword.ChangePasswordVerifyEmailScreen
import team.aliens.dms_android.feature.auth.changepassword.IdentificationScreen
import team.aliens.dms_android.feature.auth.comparepassword.ComparePasswordScreen
import team.aliens.dms_android.feature.auth.findid.FindIdScreen
import team.aliens.dms_android.feature.auth.login.LoginScreen
import team.aliens.dms_android.feature.mypage.MyPageChangePasswordScreen

fun NavGraphBuilder.authNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.Auth.Login,
        route = NavigationRoute.Auth.name,
    ){
        composable(NavigationRoute.Auth.Login) {
            LoginScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Auth.ChangePassword) {
            ChangePasswordScreen(
                navController = navController,
            )
        }

        composable(
            route = NavigationRoute.Auth.ComparePassword,
        ) {
            ComparePasswordScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Auth.FindId) {
            FindIdScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Auth.Identification) {
            IdentificationScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Auth.ChangePasswordVerifyEmail) {
            ChangePasswordVerifyEmailScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Auth.MyPageChangePassword) {
            MyPageChangePasswordScreen(
                navController = navController,
            )
        }
    }
}