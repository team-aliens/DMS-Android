package team.aliens.dms_android.feature.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.signup.ui.email.SignUpSendVerificationEmailScreen
import team.aliens.dms_android.feature.signup.ui.email.SignUpEmailVerificationCodeScreen
import team.aliens.dms_android.feature.signup.ui.id.SignUpSetIdScreen
import team.aliens.dms_android.feature.signup.ui.last.SignUpPolicyScreen
import team.aliens.dms_android.feature.signup.ui.last.SignUpSetProfileImageScreen
import team.aliens.dms_android.feature.signup.ui.password.SignUpSetPasswordScreen
import team.aliens.dms_android.feature.signup.ui.school.SignUpSchoolVerificationQuestionScreen
import team.aliens.dms_android.feature.signup.ui.school.SignUpVerifySchoolScreen

fun NavGraphBuilder.signUpNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = DmsRoute.SignUp.VerifySchool,
        route = DmsRoute.SignUp.route,
    ) {
        composable(DmsRoute.SignUp.VerifySchool) {
            SignUpVerifySchoolScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SchoolVerificationQuestion) {
            SignUpSchoolVerificationQuestionScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SendVerificationEmail) {
            SignUpSendVerificationEmailScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.CheckEmailVerificationCode) {
            SignUpEmailVerificationCodeScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SetId) {
            SignUpSetIdScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SetPassword) {
            SignUpSetPasswordScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.SetProfileImage) {
            SignUpSetProfileImageScreen(
                navController = navController,
            )
        }

        composable(DmsRoute.SignUp.Policy) {
            SignUpPolicyScreen(
                navController = navController,
            )
        }
    }
}