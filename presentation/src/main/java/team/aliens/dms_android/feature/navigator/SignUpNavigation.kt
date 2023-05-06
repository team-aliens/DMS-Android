package team.aliens.dms_android.feature.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.register.ui.email.SignUpEmailScreen
import team.aliens.dms_android.feature.register.ui.email.SignUpEmailVerifyScreen
import team.aliens.dms_android.feature.register.ui.id.SignUpIdScreen
import team.aliens.dms_android.feature.register.ui.last.SignUpPolicyScreen
import team.aliens.dms_android.feature.register.ui.last.SignUpProfileScreen
import team.aliens.dms_android.feature.register.ui.password.SignUpPasswordScreen
import team.aliens.dms_android.feature.register.ui.school.SignUpSchoolQuestionScreen
import team.aliens.dms_android.feature.register.ui.school.SignUpVerifySchoolScreen

fun NavGraphBuilder.signUpNavigation(
    navController: NavController,
){
    navigation(
        startDestination = NavigationRoute.VerifySchool,
        route = NavigationRoute.SignUp,
    ){
        composable(NavigationRoute.VerifySchool) {
            SignUpVerifySchoolScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SchoolQuestion) {
            SignUpSchoolQuestionScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpEmail) {
            SignUpEmailScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpEmailVerify) {
            SignUpEmailVerifyScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpId) {
            SignUpIdScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpPassword) {
            SignUpPasswordScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpProfile) {
            SignUpProfileScreen(
                navController = navController,
            )
        }

        composable(NavigationRoute.SignUpPolicy) {
            SignUpPolicyScreen(
                navController = navController,
            )
        }
    }
}