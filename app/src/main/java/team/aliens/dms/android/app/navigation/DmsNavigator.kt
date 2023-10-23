package team.aliens.dms.android.app.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.NavGraphSpec
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavigator
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavigator
import team.aliens.dms.android.feature.destinations.EditPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.EnterEmailScreenDestination
import team.aliens.dms.android.feature.destinations.EnterSchoolVerificationQuestionScreenDestination
import team.aliens.dms.android.feature.destinations.FindIdScreenDestination
import team.aliens.dms.android.feature.destinations.ResetPasswordEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.ResetPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.SetIdScreenDestination
import team.aliens.dms.android.feature.destinations.SetProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.SignInScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.TermsScreenDestination
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavGraph
import team.aliens.dms.android.feature.signup.navigation.SignUpNavGraph
import java.util.UUID

class DmsNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : AuthorizedNavigator,
    UnauthorizedNavigator {
    override fun openEditPasswordSetPasswordNav() {
        navController.navigate(EditPasswordSetPasswordScreenDestination)
    }

    override fun openSignIn() {
        navController.navigate(SignInScreenDestination within navGraph)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openResetPasswordEnterEmailVerificationCode() {
        navController.navigate(ResetPasswordEnterEmailVerificationCodeScreenDestination within navGraph)
    }

    override fun openResetPasswordSetPassword() {
        navController.navigate(ResetPasswordSetPasswordScreenDestination within navGraph)
    }

    override fun openAuthorizedNav() {
        navController.navigate(AuthorizedNavGraph) {
            popUpTo(UnauthorizedNavGraph) {
                inclusive = true
            }
        }
    }

    override fun openSignUpNav() {
        navController.navigate(SignUpNavGraph)
    }

    override fun openFindId() {
        navController.navigate(FindIdScreenDestination within UnauthorizedNavGraph)
    }

    override fun openResetPasswordNav() {
        navController.navigate(ResetPasswordNavGraph)
    }

    override fun openEnterSchoolVerificationQuestion() {
        navController.navigate(EnterSchoolVerificationQuestionScreenDestination within navGraph)
    }

    override fun openEnterEmail(clearStack: Boolean) {
        navController.navigate(EnterEmailScreenDestination within navGraph)
    }

    override fun openSignUpEnterEmailVerificationCode() {
        navController.navigate(SignUpEnterEmailVerificationCodeScreenDestination within navGraph)
    }

    override fun openSetId() {
        navController.navigate(SetIdScreenDestination within navGraph)
    }

    override fun openSignUpSetPassword() {
        navController.navigate(SignUpSetPasswordScreenDestination within navGraph)
    }

    override fun openSetProfileImage() {
        navController.navigate(SetProfileImageScreenDestination within navGraph)
    }

    override fun openTerms() {
        navController.navigate(TermsScreenDestination within navGraph)
    }

    override fun openStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ) {
        navController.navigate(
            StudyRoomDetailsScreenDestination/*(
                studyRoomId = studyRoomId,
                timeslot = timeslot,
            )*/ within navGraph,
        )
    }
}
