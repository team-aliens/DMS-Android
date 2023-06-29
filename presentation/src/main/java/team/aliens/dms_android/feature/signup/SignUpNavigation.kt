package team.aliens.dms_android.feature.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.signup.ui.email.SignUpEmailVerificationCodeScreen
import team.aliens.dms_android.feature.signup.ui.email.SignUpSendVerificationEmailScreen
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

/* todo sign up 작업 후 마저 구현하기
internal object SignUpNavigation {
    const val route = "signUp"

    const val EnterSchoolVerificationCode = this.route + "/enterSchoolVerificationCode"
    const val EnterSchoolVerificationQuestion = this.route + "/enterSchoolVerificationQuestion"
    const val SetEmail = this.route + "/setEmail"
    const val SetId = this.route + "/setId"
    const val SetPassword = this.route + "/setPassword"
    const val SetProfile = this.route + "/setProfile"
    const val Terms = this.route + "/terms"
}

internal fun NavGraphBuilder.signUpNavigation() {
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
*/
