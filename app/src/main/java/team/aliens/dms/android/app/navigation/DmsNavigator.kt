package team.aliens.dms.android.app.navigation

import androidx.navigation.NavController
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.NavGraphSpec
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavigator
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavigator
import team.aliens.dms.android.feature.destinations.EditPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.EditProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.EnterEmailScreenDestination
import team.aliens.dms.android.feature.destinations.EnterSchoolVerificationQuestionScreenDestination
import team.aliens.dms.android.feature.destinations.FindIdScreenDestination
import team.aliens.dms.android.feature.destinations.NoticeDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.NotificationBoxScreenDestination
import team.aliens.dms.android.feature.destinations.PointHistoryScreenDestination
import team.aliens.dms.android.feature.destinations.RemainsApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.ResetPasswordEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.ResetPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.SetIdScreenDestination
import team.aliens.dms.android.feature.destinations.SetProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.SignInScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpEnterEmailVerificationCodeScreenDestination
import team.aliens.dms.android.feature.destinations.SignUpSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomDetailsScreenDestination
import team.aliens.dms.android.feature.destinations.StudyRoomListScreenDestination
import team.aliens.dms.android.feature.destinations.TermsScreenDestination
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavGraph
import team.aliens.dms.android.feature.signup.navigation.SignUpNavGraph
import java.util.UUID

class DmsNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController,
) : AuthorizedNavigator, UnauthorizedNavigator {

    override fun openNotificationBox() {
        navController.navigate(NotificationBoxScreenDestination within navGraph)
    }

    override fun openUnauthorizedNav() {
        navController.navigate(UnauthorizedNavGraph) {
            popUpTo(AuthorizedNavGraph) {
                inclusive = true
            }
        }
    }

    override fun openStudyRoomList() {
        navController.navigate(StudyRoomListScreenDestination within navGraph)
    }

    override fun openRemainsApplication() {
        navController.navigate(RemainsApplicationScreenDestination within navGraph) {
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun openEditProfileImage() {
        navController.navigate(EditProfileImageScreenDestination() within AuthorizedNavGraph)
    }

    override fun openPointHistory() {
        navController.navigate(PointHistoryScreenDestination within navGraph)
    }

    override fun openEditPasswordNav() {
        navController.navigate(EditPasswordNavGraph)
    }

    override fun openNoticeDetails(noticeId: UUID) {
        navController.navigate(NoticeDetailsScreenDestination(noticeId) within navGraph)
    }

    override fun openEditPasswordSetPassword() {
        navController.navigate(EditPasswordSetPasswordScreenDestination within EditPasswordNavGraph) {
            navController.popBackStack(
                route = EditPasswordNavGraph.startRoute,
                inclusive = true,
            )
        }
    }

    override fun openSignIn() {
        navController.navigate(SignInScreenDestination within navGraph)
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
            popUpTo(UnauthorizedNavGraph) { inclusive = true }
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
        // TODO: make custom navigation argument
        studyRoomId: UUID,
        studyRoomName: String,
        timeslot: UUID,
        studyRoomApplicationStartTime: String,
        studyRoomApplicationEndTime: String,
    ) {
        navController.navigate(
            StudyRoomDetailsScreenDestination(
                studyRoomId = studyRoomId,
                studyRoomName = studyRoomName,
                timeslot = timeslot,
                studyRoomApplicationStartTime = studyRoomApplicationStartTime,
                studyRoomApplicationEndTime = studyRoomApplicationEndTime,
            ) within navGraph,
        )
    }
}
