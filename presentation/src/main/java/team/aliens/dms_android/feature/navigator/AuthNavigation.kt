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
        startDestination = NavigationRoute.Login,
        route = NavigationRoute.Auth,
    ){
        composable(NavigationRoute.Login) {
            LoginScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.ChangePassword) {
            ChangePasswordScreen(
                navController = navController,
            )
        }

        composable(
            route = NavigationRoute.ComparePassword,
        ) {
            ComparePasswordScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.FindId) {
            FindIdScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.Identification) {
            IdentificationScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.ChangePasswordVerifyEmail) {
            ChangePasswordVerifyEmailScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.MyPageChangePassword) {
            MyPageChangePasswordScreen(
                navController = navController,
            )
        }
    }
}