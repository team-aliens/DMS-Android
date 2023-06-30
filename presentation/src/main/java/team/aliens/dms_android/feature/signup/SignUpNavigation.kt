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
import team.aliens.dms_android.feature.signup.ui.last.TermsScreen
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
    const val verifySchool = this.route + "/verifySchool"
    const val verifyEmail = this.route + "/verifyEmail"
    const val setUserInformation = this.route + "/setUserInformation"

    object VerifySchool {
        const val EnterSchoolVerificationCode = "$verifySchool/enterSchoolVerificationCode"
        const val EnterSchoolVerificationQuestion = "$verifySchool/enterSchoolVerificationQuestion"
    }

    object VerifyEmail {
        const val SetEmail = "$verifyEmail/setEmail"
        const val VerifyEmail = "$verifyEmail/verifyEmail"
    }

    object SetUserInformation {
        const val SetId = "$setUserInformation/setId"
        const val SetPassword = "$setUserInformation/setPassword"
        const val SetProfile = "$setUserInformation/setProfile"
    }

    const val Terms = this.route + "/terms"
}

internal fun NavGraphBuilder.signUpNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = SignUpNavigation.verifySchool,
        route = SignUpNavigation.route,
    ) {
        verifySchoolNavigation(
            navController = navController,
        )

        verifyEmailNavigation(
            navController = navController,
        )

        setUserInformationNavigation(
            navController = navController,
        )

        composable(SignUpNavigation.Terms) {
            TermsScreen(
                navController = navController,
            )
        }
    }
}

private fun NavGraphBuilder.verifySchoolNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = SignUpNavigation.VerifySchool.EnterSchoolVerificationCode,
        route = SignUpNavigation.verifySchool,
    ) {
        composable(SignUpNavigation.VerifySchool.EnterSchoolVerificationCode) {
            EnterSchoolVerificationCodeScreen(
                navController = navController,
            )
        }

        composable(SignUpNavigation.VerifySchool.EnterSchoolVerificationQuestion) {
            EnterSchoolVerificationQuestionScreen(
                navController = navController,
            )
        }
    }
}

private fun NavGraphBuilder.verifyEmailNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = SignUpNavigation.VerifyEmail.SetEmail,
        route = SignUpNavigation.verifyEmail,
    ) {
        composable(SignUpNavigation.VerifyEmail.SetEmail) {
            SendVerificationEmailScreen(
                navController = navController,
            )
        }

        composable(SignUpNavigation.VerifyEmail.VerifyEmail) {
            VerifyEmailScreen(
                navController = navController,
            )
        }
    }
}

private fun NavGraphBuilder.setUserInformationNavigation(
    navController: NavController,
) {
    navigation(
        startDestination = SignUpNavigation.SetUserInformation.SetId,
        route = SignUpNavigation.setUserInformation,
    ) {
        composable(SignUpNavigation.SetUserInformation.SetId) {
            SetIdScreen(
                navController = navController,
            )
        }

        composable(SignUpNavigation.SetUserInformation.SetPassword) {
            SetPasswordScreen(
                navController = navController,
            )
        }

        composable(SignUpNavigation.SetUserInformation.SetProfile) {
            SetProfileImageScreen(
                navController = navController,
            )
        }
    }
}

