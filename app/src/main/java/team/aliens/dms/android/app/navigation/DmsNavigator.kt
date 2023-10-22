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
import team.aliens.dms.android.feature._legacy.util.SelectImageType
import team.aliens.dms.android.feature.destinations.AnnouncementListScreenDestination
import team.aliens.dms.android.feature.destinations.ApplicationScreenDestination
import team.aliens.dms.android.feature.destinations.EditPasswordSetPasswordScreenDestination
import team.aliens.dms.android.feature.destinations.EditProfileImageScreenDestination
import team.aliens.dms.android.feature.destinations.EnterEmailScreenDestination
import team.aliens.dms.android.feature.destinations.EnterSchoolVerificationQuestionScreenDestination
import team.aliens.dms.android.feature.destinations.FindIdScreenDestination
import team.aliens.dms.android.feature.destinations.HomeScreenDestination
import team.aliens.dms.android.feature.destinations.MyPageScreenDestination
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

    override fun openHome() {
        navController.navigate(HomeScreenDestination within navGraph)
    }

    override fun openApplication() {
        navController.navigate(ApplicationScreenDestination within navGraph)
    }

    override fun openAnnouncementList() {
        navController.navigate(AnnouncementListScreenDestination within navGraph)
    }

    override fun openMyPage() {
        navController.navigate(MyPageScreenDestination within navGraph)
    }

    override fun openNoticeDetails(noticeId: UUID) {
        navController.navigate(NoticeDetailsScreenDestination(noticeId = noticeId) within navGraph)
    }

    override fun openStudyRoomList() {
        navController.navigate(StudyRoomListScreenDestination within navGraph)
    }

    override fun openRemainsApplication() {
        navController.navigate(RemainsApplicationScreenDestination within navGraph)
    }

    override fun openNotificationBox() {
        navController.navigate(NotificationBoxScreenDestination within navGraph)
    }

    override fun openPointHistory() {
        navController.navigate(PointHistoryScreenDestination within navGraph)
    }

    override fun openEditPasswordNav() {
        navController.navigate(EditPasswordNavGraph) {
            restoreState = true
            launchSingleTop = true
        }
    }

    override fun openEditProfileImage(selectImageType: SelectImageType) {
        // TODO
        navController.navigate(EditProfileImageScreenDestination(selectImageType) within navGraph)
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

    override fun openUnauthorizedNav() {
        navController.navigate(UnauthorizedNavGraph) {
            popUpTo(AuthorizedNavGraph) {
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
