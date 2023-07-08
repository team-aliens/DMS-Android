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
import androidx.compose.runtime.Composable
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
import team.aliens.dms_android.util.composableActivityViewModel

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

private val signUpViewModel
    @Composable get() = composableActivityViewModel<SignUpViewModel>()

internal fun NavGraphBuilder.signUpNavigation(
    onNavigateToEnterSchoolQuestion: () -> Unit,
    onNavigateToSendVerificationEmail: () -> Unit,
    onNavigateToVerifyEmail: () -> Unit,
    onNavigateToSetId: () -> Unit,
    onNavigateToSetPassword: () -> Unit,
    onNavigateToSetProfileImage: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
    onNavigateToSetEmailWithInclusive: () -> Unit,
) {
    navigation(
        startDestination = SignUpNavigation.verifySchool,
        route = SignUpNavigation.route,
    ) {
        verifySchoolNavigation(
            onNavigateToEnterSchoolQuestion = onNavigateToEnterSchoolQuestion,
            onNavigateToSendVerificationEmail = onNavigateToSendVerificationEmail,
            onNavigateToSignInWithInclusive = onNavigateToSignInWithInclusive,
        )

        verifyEmailNavigation(
            onNavigateToVerifyEmail = onNavigateToVerifyEmail,
            onNavigateToSignInWithInclusive = onNavigateToSignInWithInclusive,
            onNavigateToSetId = onNavigateToSetId,
            onNavigateToSetEmailWithInclusive = onNavigateToSetEmailWithInclusive,
        )

        setUserInformationNavigation(
            onNavigateToSetPassword = onNavigateToSetPassword,
            onNavigateToSetProfileImage = onNavigateToSetProfileImage,
            onNavigateToTerms = onNavigateToTerms,
        )

        composable(SignUpNavigation.Terms) {
            TermsScreen(
                onNavigateToTerms = onNavigateToTerms,
                onNavigateToSignInWithInclusive = onNavigateToSignInWithInclusive,
                signUpViewModel = signUpViewModel,
            )
        }
    }
}

private fun NavGraphBuilder.verifySchoolNavigation(
    onNavigateToEnterSchoolQuestion: () -> Unit,
    onNavigateToSendVerificationEmail: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
) {
    navigation(
        startDestination = SignUpNavigation.VerifySchool.EnterSchoolVerificationCode,
        route = SignUpNavigation.verifySchool,
    ) {
        composable(SignUpNavigation.VerifySchool.EnterSchoolVerificationCode) {
            EnterSchoolVerificationCodeScreen(
                onNavigateToEnterSchoolQuestion = onNavigateToEnterSchoolQuestion,
                signUpViewModel = signUpViewModel,
            )
        }

        composable(SignUpNavigation.VerifySchool.EnterSchoolVerificationQuestion) {
            EnterSchoolVerificationQuestionScreen(
                onNavigateToSendVerificationEmail = onNavigateToSendVerificationEmail,
                onNavigateToSignInWithInclusive = onNavigateToSignInWithInclusive,
                signUpViewModel = signUpViewModel,
            )
        }
    }
}

private fun NavGraphBuilder.verifyEmailNavigation(
    onNavigateToVerifyEmail: () -> Unit,
    onNavigateToSignInWithInclusive: () -> Unit,
    onNavigateToSetEmailWithInclusive: () -> Unit,
    onNavigateToSetId: () -> Unit,
) {
    navigation(
        startDestination = SignUpNavigation.VerifyEmail.SetEmail,
        route = SignUpNavigation.verifyEmail,
    ) {
        composable(SignUpNavigation.VerifyEmail.SetEmail) {
            SendVerificationEmailScreen(
                onNavigateToVerifyEmail = onNavigateToVerifyEmail,
                onNavigateToSignInWithInclusive = onNavigateToSignInWithInclusive,
                signUpViewModel = signUpViewModel,
            )
        }

        composable(SignUpNavigation.VerifyEmail.VerifyEmail) {
            VerifyEmailScreen(
                onNavigateToSetId = onNavigateToSetId,
                onNavigateToSetEmailWithInclusive = onNavigateToSetEmailWithInclusive,
                signUpViewModel = signUpViewModel,
            )
        }
    }
}

private fun NavGraphBuilder.setUserInformationNavigation(
    onNavigateToSetPassword: () -> Unit,
    onNavigateToSetProfileImage: () -> Unit,
    onNavigateToTerms: () -> Unit,
) {
    navigation(
        startDestination = SignUpNavigation.SetUserInformation.SetId,
        route = SignUpNavigation.setUserInformation,
    ) {
        composable(SignUpNavigation.SetUserInformation.SetId) {
            SetIdScreen(
                onNavigateToSetPassword = onNavigateToSetPassword,
                signUpViewModel = signUpViewModel,
            )
        }

        composable(SignUpNavigation.SetUserInformation.SetPassword) {
            SetPasswordScreen(
                onNavigateToSetProfile = onNavigateToSetProfileImage,
                signUpViewModel = signUpViewModel,
            )
        }

        composable(SignUpNavigation.SetUserInformation.SetProfile) {
            SetProfileImageScreen(
                onNavigateToTerms = onNavigateToTerms,
                signUpViewModel = signUpViewModel,
            )
        }
    }
}

