package team.aliens.dms_android.feature.signup

/*import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.feature.signup.ui.email.SignUpEmailVerificationCodeScreen
import team.aliens.dms_android.feature.signup.ui.email.SignUpSendVerificationEmailScreen
import team.aliens.dms_android.feature.signup.ui.id.SignUpSetIdScreen
import team.aliens.dms_android.feature.signup.ui.last.SignUpPolicyScreen
import team.aliens.dms_android.feature.signup.ui.last.SignUpSetProfileImageScreen
import team.aliens.dms_android.feature.signup.ui.password.SignUpSetPasswordScreen
import team.aliens.dms_android.feature.signup.ui.school.SignUpSchoolVerificationQuestionScreen
import team.aliens.dms_android.feature.signup.ui.school.SignUpVerifySchoolScreen
import androidx.navigation.NavController*/
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.signup.ui.email.SendVerificationEmailScreen
import team.aliens.dms_android.feature.signup.ui.email.VerifyEmailScreen
import team.aliens.dms_android.feature.signup.ui.id.SetIdScreen
import team.aliens.dms_android.feature.signup.ui.last.SetProfileImageScreen
import team.aliens.dms_android.feature.signup.ui.password.SetPasswordScreen
import team.aliens.dms_android.feature.signup.ui.school.EnterSchoolVerificationCodeScreen
import team.aliens.dms_android.feature.signup.ui.school.EnterSchoolVerificationQuestionScreen

/*
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
}*/
// todo sign up 작업 후 마저 구현하기
internal object SignUpNavigation {
    const val route = "signUp"

    const val EnterSchoolVerificationCode = this.route + "/enterSchoolVerificationCode"
    const val EnterSchoolVerificationQuestion = this.route + "/enterSchoolVerificationQuestion"
    const val SetEmail = this.route + "/setEmail"
    const val VerifyEmail = this.route + "/verifyEmail"
    const val SetId = this.route + "/setId"
    const val SetPassword = this.route + "/setPassword"
    const val SetProfile = this.route + "/setProfile"
    const val Terms = this.route + "/terms"
}

internal fun NavGraphBuilder.signUpNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = SignUpNavigation.EnterSchoolVerificationCode,
        route = SignUpNavigation.route,
    ) {
        composable(SignUpNavigation.EnterSchoolVerificationCode) {
            EnterSchoolVerificationCodeScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.EnterSchoolVerificationQuestion) {
            EnterSchoolVerificationQuestionScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.SetEmail) {
            SendVerificationEmailScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.VerifyEmail){
            VerifyEmailScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.SetId) {
            SetIdScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.SetPassword) {
            SetPasswordScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.SetProfile) {
            SetProfileImageScreen(
                navController = navController,
            )
        }
        composable(SignUpNavigation.Terms) {
            TermsScreen(
                navController = navController,
            )
        }
    }
}

