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
) {
    navigation(
        startDestination = DmsRoute.SignUp.ExamineSchoolCode,
        route = DmsRoute.SignUp.route,
    ) {
        composable(DmsRoute.SignUp.ExamineSchoolCode) {
            SignUpVerifySchoolScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SchoolQuestion) {
            SignUpSchoolQuestionScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SignUpEmail) {
            SignUpEmailScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SignUpEmailVerify) {
            SignUpEmailVerifyScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SignUpId) {
            SignUpIdScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SignUpPassword) {
            SignUpPasswordScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SignUpProfile) {
            SignUpProfileScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SignUpPolicy) {
            SignUpPolicyScreen(
                navController = navController,
            )
        }
    }
}