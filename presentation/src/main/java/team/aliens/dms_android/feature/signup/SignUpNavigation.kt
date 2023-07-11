package team.aliens.dms_android.feature.signup

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import team.aliens.dms_android.feature.signup.verifyemail.SendVerificationEmailScreen
import team.aliens.dms_android.feature.signup.verifyemail.VerifyEmailScreen
import team.aliens.dms_android.feature.signup.setuserinformation.SetIdScreen
import team.aliens.dms_android.feature.signup.setuserinformation.SetProfileImageScreen
import team.aliens.dms_android.feature.signup.setuserinformation.SetPasswordScreen
import team.aliens.dms_android.feature.signup.terms.TermsScreen
import team.aliens.dms_android.feature.signup.verifyschool.EnterSchoolVerificationCodeScreen
import team.aliens.dms_android.feature.signup.verifyschool.EnterSchoolVerificationQuestionScreen
import team.aliens.dms_android.util.composableActivityViewModel

// todo sign up 작업 후 마저 구현하기
internal object SignUpNavigation {
    const val route = "signUp"

    const val VerifySchoolRoute = this.route + "/verifySchool"
    const val VerifyEmailRoute = this.route + "/verifyEmail"
    const val SetUserInformationRoute = this.route + "/setUserInformation"


    object VerifySchool {
        const val EnterSchoolVerificationCode = "$VerifySchoolRoute/enterSchoolVerificationCode"
        const val EnterSchoolVerificationQuestion = "$VerifySchoolRoute/enterSchoolVerificationQuestion"
    }

    object VerifyEmail {
        const val SetEmail = "$VerifyEmailRoute/setEmail"
        const val VerifyEmail = "$VerifyEmailRoute/verifyEmail"
    }

    object SetUserInformation {
        const val SetId = "$SetUserInformationRoute/setId"
        const val SetPassword = "$SetUserInformationRoute/setPassword"
        const val SetProfile = "$SetUserInformationRoute/setProfile"
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
        startDestination = SignUpNavigation.SetUserInformationRoute,
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
        route = SignUpNavigation.VerifySchoolRoute,
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
        route = SignUpNavigation.VerifyEmailRoute,
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
        route = SignUpNavigation.SetUserInformationRoute,
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

